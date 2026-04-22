param(
    [ValidateSet("app-image", "exe", "msi", "pkg", "dmg", "deb", "rpm")]
    [string]$Type = "app-image",
    [string]$Name = "password-manager-cli",
    [string]$MainClass = "com.project.password.manager.Application"
)

$ErrorActionPreference = "Stop"

function Get-CurrentPlatform {
    if ([System.Runtime.InteropServices.RuntimeInformation]::IsOSPlatform([System.Runtime.InteropServices.OSPlatform]::Windows)) {
        return "windows"
    }
    if ([System.Runtime.InteropServices.RuntimeInformation]::IsOSPlatform([System.Runtime.InteropServices.OSPlatform]::Linux)) {
        return "linux"
    }
    if ([System.Runtime.InteropServices.RuntimeInformation]::IsOSPlatform([System.Runtime.InteropServices.OSPlatform]::OSX)) {
        return "macos"
    }

    throw "Unsupported operating system for packaging."
}

function Assert-InstallerPrerequisites {
    param(
        [string]$Platform,
        [string]$RequestedType
    )

    if ($Platform -eq "windows" -and $RequestedType -in @("exe", "msi")) {
        $wixLight = Get-Command light.exe -ErrorAction SilentlyContinue
        $wixCandle = Get-Command candle.exe -ErrorAction SilentlyContinue
        if ($null -eq $wixLight -or $null -eq $wixCandle) {
            throw "WiX Toolset is required for jpackage installer type '$RequestedType' on Windows. Install WiX and add light.exe and candle.exe to PATH, or use -Type app-image."
        }
    }
}

function New-LinuxLauncher {
    param(
        [string]$DistDir,
        [string]$Name
    )

    $launcherPath = Join-Path $DistDir "$Name.sh"
    $content = @(
        '#!/usr/bin/env bash',
        'set -euo pipefail',
        'SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"',
        ('exec "$SCRIPT_DIR/{0}/bin/{0}" "$@"' -f $Name)
    ) -join "`n"
    Set-Content -Path $launcherPath -Value $content -NoNewline
    & chmod +x $launcherPath
}

function New-MacLauncher {
    param(
        [string]$DistDir,
        [string]$Name
    )

    $launcherPath = Join-Path $DistDir "$Name.command"
    $content = @(
        '#!/bin/bash',
        'set -euo pipefail',
        'SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"',
        ('exec "$SCRIPT_DIR/{0}.app/Contents/MacOS/{0}" "$@"' -f $Name)
    ) -join "`n"
    Set-Content -Path $launcherPath -Value $content -NoNewline
    & chmod +x $launcherPath
}

if (-not (Get-Command mvn -ErrorAction SilentlyContinue)) {
    throw "Maven is required on the build machine to package the application."
}

if (-not (Get-Command jpackage -ErrorAction SilentlyContinue)) {
    throw "jpackage was not found. Install a JDK that includes jpackage, such as JDK 17+."
}

$platform = Get-CurrentPlatform
Assert-InstallerPrerequisites -Platform $platform -RequestedType $Type

$projectRoot = Split-Path -Parent $PSScriptRoot
$targetDir = Join-Path $projectRoot "target"
$distDir = Join-Path $projectRoot "dist"
$pomPath = Join-Path $projectRoot "pom.xml"

[xml]$pom = Get-Content -Path $pomPath
$pomVersion = $pom.project.version
if ([string]::IsNullOrWhiteSpace($pomVersion)) {
    throw "Unable to read the project version from pom.xml."
}

$appVersionMatch = [regex]::Match($pomVersion, "\d+(\.\d+){0,2}")
if (-not $appVersionMatch.Success) {
    throw "The pom.xml version '$pomVersion' does not contain a jpackage-compatible numeric version."
}

$appVersion = $appVersionMatch.Value

Push-Location $projectRoot
try {
    & mvn -DskipTests package
    if ($LASTEXITCODE -ne 0) {
        throw "Maven package failed with exit code $LASTEXITCODE."
    }

    $jar = Get-ChildItem -Path $targetDir -Filter "*-standalone.jar" | Sort-Object LastWriteTime -Descending | Select-Object -First 1
    if ($null -eq $jar) {
        throw "No shaded standalone jar was found in target/. Run Maven packaging first."
    }

    if (Test-Path $distDir) {
        Remove-Item -Path $distDir -Recurse -Force
    }
    New-Item -ItemType Directory -Path $distDir | Out-Null

    $arguments = @(
        "--type", $Type,
        "--input", $targetDir,
        "--dest", $distDir,
        "--name", $Name,
        "--app-version", $appVersion,
        "--vendor", "com.project",
        "--main-jar", $jar.Name,
        "--main-class", $MainClass
    )

    if ($platform -eq "windows") {
        $arguments += "--win-console"
    }

    if ($platform -eq "windows" -and $Type -ne "app-image") {
        $arguments += @(
            "--win-dir-chooser",
            "--win-menu",
            "--win-shortcut"
        )
    }

    & jpackage @arguments
    if ($LASTEXITCODE -ne 0) {
        throw "jpackage failed with exit code $LASTEXITCODE."
    }

    if ($Type -eq "app-image") {
        switch ($platform) {
            "linux" {
                New-LinuxLauncher -DistDir $distDir -Name $Name
            }
            "macos" {
                New-MacLauncher -DistDir $distDir -Name $Name
            }
        }
    }

    Write-Host "Package created in $distDir"
}
finally {
    Pop-Location
}
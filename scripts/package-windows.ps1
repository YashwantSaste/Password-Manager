param(
    [ValidateSet("app-image", "exe", "msi")]
    [string]$Type = "app-image",
    [string]$Name = "password-manager-cli",
    [string]$MainClass = "com.project.password.manager.Application"
)

$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$packageScript = Join-Path $scriptDir "package-app.ps1"

& $packageScript -Type $Type -Name $Name -MainClass $MainClass
if ($LASTEXITCODE -ne 0) {
    exit $LASTEXITCODE
}
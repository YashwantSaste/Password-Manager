package com.project.password.manager.cli.commands;

import com.project.password.manager.cli.runtime.CliTheme;

import picocli.CommandLine.Command;

@Command(name = "vault", mixinStandardHelpOptions = true, description = "Vault operations.", subcommands = {
		VaultListCommand.class,
		VaultCreateCommand.class,
		VaultDefaultCommand.class,
		picocli.CommandLine.HelpCommand.class })
public class VaultCommand implements Runnable {

	@Override
	public void run() {
		System.out.println(CliTheme.hintPanel("Vault Commands",
				CliTheme.key("use") + CliTheme.muted(" : ") + CliTheme.secondary("list, create, or default")));
	}
}
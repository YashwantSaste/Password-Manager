package com.project.password.manager.cli.commands;

import picocli.CommandLine.Command;

@Command(name = "vault", mixinStandardHelpOptions = true, description = "Vault operations.", subcommands = {
		VaultListCommand.class,
		VaultCreateCommand.class,
		VaultDefaultCommand.class,
		picocli.CommandLine.HelpCommand.class })
public class VaultCommand implements Runnable {

	@Override
	public void run() {
		System.out.println("Use a vault subcommand like list, create, or default.");
	}
}
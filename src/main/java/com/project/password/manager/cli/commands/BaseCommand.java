package com.project.password.manager.cli.commands;

import picocli.CommandLine.Command;

@Command(name = "password-manager", mixinStandardHelpOptions = true, description = "Interactive password manager CLI.",
		subcommands = {
				LoginCommand.class,
				SignupCommand.class,
				LogoutCommand.class,
				WhoAmICommand.class,
				PingCommand.class,
				VaultCommand.class,
				EntryCommand.class,
				picocli.CommandLine.HelpCommand.class })
public class BaseCommand implements Runnable {

	@Override
	public void run() {
		System.out.println("Type help to list commands or exit to close the session.");
	}
}

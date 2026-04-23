package com.project.password.manager.cli.commands;

import com.project.password.manager.cli.runtime.CliTheme;

import picocli.CommandLine.Command;

@Command(name = "password-manager", mixinStandardHelpOptions = true, description = "Interactive password manager CLI.",
		subcommands = {
				LoginCommand.class,
				SignupCommand.class,
				LogoutCommand.class,
				WhoAmICommand.class,
				PingCommand.class,
				ConfigCommand.class,
				ThemeCommand.class,
				VaultCommand.class,
				EntryCommand.class,
				picocli.CommandLine.HelpCommand.class })
public class BaseCommand implements Runnable {

	@Override
	public void run() {
		System.out.println(CliTheme.hintPanel("Password Manager CLI",
				CliTheme.key("theme") + CliTheme.muted(" : ") + CliTheme.secondary(CliTheme.getActiveThemeName()),
				CliTheme.key("next") + CliTheme.muted(" : ") + CliTheme.accent("help") + CliTheme.muted("  ·  ")
						+ CliTheme.accent("config list") + CliTheme.muted("  ·  ") + CliTheme.accent("vault list")));
	}
}

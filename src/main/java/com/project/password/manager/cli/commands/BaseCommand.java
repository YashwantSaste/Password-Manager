package com.project.password.manager.cli.commands;

import com.project.password.manager.cli.commands.auth.LoginCommand;
import com.project.password.manager.cli.commands.auth.LogoutCommand;
import com.project.password.manager.cli.commands.auth.PingCommand;
import com.project.password.manager.cli.commands.auth.SignupCommand;
import com.project.password.manager.cli.commands.auth.WhoAmICommand;
import com.project.password.manager.cli.commands.config.ConfigCommand;
import com.project.password.manager.cli.commands.entry.EntryCommand;
import com.project.password.manager.cli.runtime.CliTheme;
import com.project.password.manager.cli.commands.theme.ThemeCommand;
import com.project.password.manager.cli.commands.user.UserCommand;
import com.project.password.manager.cli.commands.vault.VaultCommand;

import picocli.CommandLine.Command;

@Command(name = "password-manager", mixinStandardHelpOptions = true, description = "Interactive password manager CLI.",
		subcommands = {
				LoginCommand.class,
				SignupCommand.class,
				LogoutCommand.class,
				WhoAmICommand.class,
				PingCommand.class,
				UserCommand.class,
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
						+ CliTheme.accent("config list") + CliTheme.muted("  ·  ") + CliTheme.accent("user role list admin")
						+ CliTheme.muted("  ·  ") + CliTheme.accent("vault list")));
	}
}

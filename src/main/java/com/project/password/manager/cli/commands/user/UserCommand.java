package com.project.password.manager.cli.commands.user;

import com.project.password.manager.cli.runtime.CliTheme;

import picocli.CommandLine.Command;

@Command(name = "user", mixinStandardHelpOptions = true, description = "User inspection and administration commands.", subcommands = {
		UserRoleCommand.class,
		picocli.CommandLine.HelpCommand.class })
public class UserCommand implements Runnable {

	@Override
	public void run() {
		System.out.println(CliTheme.hintPanel("User Commands",
				CliTheme.key("roles") + CliTheme.muted(" : ") + CliTheme.secondary("user role list <username>"),
				CliTheme.key("grant") + CliTheme.muted(" : ") + CliTheme.secondary("user role grant <username> ADMIN"),
				CliTheme.key("revoke") + CliTheme.muted(" : ") + CliTheme.secondary("user role revoke <username> ADMIN")));
	}
}
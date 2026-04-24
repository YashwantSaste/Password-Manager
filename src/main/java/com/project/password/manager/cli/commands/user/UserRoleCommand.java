package com.project.password.manager.cli.commands.user;

import com.project.password.manager.cli.runtime.CliTheme;

import picocli.CommandLine.Command;

@Command(name = "role", mixinStandardHelpOptions = true, description = "Manage user roles.", subcommands = {
		UserRoleListCommand.class,
		UserRoleGrantCommand.class,
		UserRoleRevokeCommand.class,
		picocli.CommandLine.HelpCommand.class })
public class UserRoleCommand implements Runnable {

	@Override
	public void run() {
		System.out.println(CliTheme.hintPanel("Role Management",
				CliTheme.key("inspect") + CliTheme.muted(" : ") + CliTheme.secondary("user role list <username>"),
				CliTheme.key("grant") + CliTheme.muted(" : ") + CliTheme.secondary("user role grant <username> ADMIN"),
				CliTheme.key("revoke") + CliTheme.muted(" : ") + CliTheme.secondary("user role revoke <username> ADMIN")));
	}
}
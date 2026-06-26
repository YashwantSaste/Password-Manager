package com.project.password.manager.cli.commands.customization.role;

import com.project.password.manager.cli.runtime.CliTheme;

import picocli.CommandLine.Command;

@Command(name = "role", mixinStandardHelpOptions = true, description = "Custom Role operations.", subcommands = {
		CustomRoleListCommand.class, CustomRoleGetCommand.class, CustomRoleCreateCommand.class,
		picocli.CommandLine.HelpCommand.class })
public class CustomRoleCommand implements Runnable {

	@Override
	public void run() {
		System.out.println(CliTheme.hintPanel("Custom Role Commands",
				CliTheme.key("use") + CliTheme.muted(" : ") + CliTheme.secondary("list, get, create, update, delete")));
	}

}

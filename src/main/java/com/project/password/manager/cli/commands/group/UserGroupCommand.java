package com.project.password.manager.cli.commands.group;

import com.project.password.manager.cli.runtime.CliTheme;

import picocli.CommandLine.Command;

@Command(name = "group", mixinStandardHelpOptions = true, description = "User group operations.", subcommands = {
		UserGroupListCommand.class, UserGroupGetCommand.class, UserGroupCreateCommand.class, UserGroupAddUserCommand.class,
		UserGroupRemoveUserCommand.class, picocli.CommandLine.HelpCommand.class })
public class UserGroupCommand implements Runnable {

	@Override
	public void run() {
		System.out.println(CliTheme.hintPanel("User Group Commands",
				CliTheme.key("list") + CliTheme.muted(" : ") + CliTheme.secondary("group list"),
				CliTheme.key("get") + CliTheme.muted(" : ") + CliTheme.secondary("group get --groupId admins"),
				CliTheme.key("create") + CliTheme.muted(" : ")
						+ CliTheme.secondary("group create --groupId admins --name Admins --users user1,user2")));
	}
}

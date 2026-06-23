package com.project.password.manager.cli.commands.team;

import com.project.password.manager.cli.runtime.CliTheme;

import picocli.CommandLine.Command;

@Command(name = "team", mixinStandardHelpOptions = true, description = "Manage your teams (By default Admins are only allowed to create team. Admin allow users to create by disabling the flag app.team.create.allow.admin.only in password-manager.properties).", subcommands = {
		GetTeamCommand.class, ListTeamCommand.class, CreateTeamCommand.class, TeamVaultCommand.class,
		picocli.CommandLine.HelpCommand.class })
public class TeamCommand implements Runnable {

	@Override
	public void run() {
		System.out.println(CliTheme.hintPanel("Team Commands",
				CliTheme.key("list") + CliTheme.muted(" : ") + CliTheme.secondary("team list"),
				CliTheme.key("get") + CliTheme.muted(" : ") + CliTheme.secondary("team get team-name"),
				CliTheme.key("create") + CliTheme.muted(" : ") + CliTheme.secondary("team create team-name"),
				CliTheme.key("vault") + CliTheme.muted(" : ") + CliTheme.secondary("team vault list team-name")));
	}

}

package com.project.password.manager.cli.commands.team;

import com.project.password.manager.cli.runtime.CliTheme;

import picocli.CommandLine.Command;

@Command(name = "vault", mixinStandardHelpOptions = true, description = "Manage team vaults.", subcommands = {
		TeamVaultListCommand.class, TeamVaultCreateCommand.class, picocli.CommandLine.HelpCommand.class })
public class TeamVaultCommand implements Runnable {

	@Override
	public void run() {
		System.out.println(CliTheme.hintPanel("Team Vault Commands",
				CliTheme.key("list") + CliTheme.muted(" : ") + CliTheme.secondary("team vault list team-name"),
				CliTheme.key("create") + CliTheme.muted(" : ")
						+ CliTheme.secondary("team vault create team-name vault-name")));
	}
}
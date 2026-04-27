package com.project.password.manager.cli.commands.team;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.cli.commands.DelegatingCliCommand;
import com.project.password.manager.cli.handlers.team.TeamVaultListCommandHandler;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "list", mixinStandardHelpOptions = true, description = "Lists vaults for a team accessible to the authenticated user.")
public class TeamVaultListCommand extends DelegatingCliCommand<TeamVaultListCommand.Request, TeamVaultListCommandHandler> {

	@Parameters(index = "0", paramLabel = "teamId", description = "Team identifier")
	private String teamId;

	@Option(names = "--show-ids", description = "Shows internal vault identifiers in the output.")
	private boolean showIds;

	@Override
	@NotNull
	protected Request buildRequest() {
		return new Request(teamId, showIds);
	}

	public static final class Request {
		@NotNull
		private final String teamId;
		private final boolean showIds;

		public Request(@NotNull String teamId, boolean showIds) {
			this.teamId = teamId;
			this.showIds = showIds;
		}

		@NotNull
		public String getTeamId() {
			return teamId;
		}

		public boolean isShowIds() {
			return showIds;
		}
	}
}
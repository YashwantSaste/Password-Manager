package com.project.password.manager.cli.commands.team;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.cli.commands.DelegatingCliCommand;
import com.project.password.manager.cli.handlers.team.GetTeamCommandHandler;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "get", mixinStandardHelpOptions = true, description = "Shows details for a team accessible to the authenticated user.")
public class GetTeamCommand extends DelegatingCliCommand<GetTeamCommand.Request, GetTeamCommandHandler> {

	@Parameters(index = "0", paramLabel = "teamId", description = "Team identifier")
	private String teamId;

	@Override
	@NotNull
	protected Request buildRequest() {
		return new Request(teamId);
	}

	public static final class Request {
		@NotNull
		private final String teamId;

		public Request(@NotNull String teamId) {
			this.teamId = teamId;
		}

		@NotNull
		public String getTeamId() {
			return teamId;
		}
	}

}

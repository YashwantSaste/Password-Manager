package com.project.password.manager.cli.commands.team;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.cli.commands.DelegatingCliCommand;
import com.project.password.manager.cli.handlers.team.CreateTeamCommandHandler;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "create", mixinStandardHelpOptions = true, description = "Creates a new team.")
public class CreateTeamCommand extends DelegatingCliCommand<CreateTeamCommand.Request, CreateTeamCommandHandler> {

	@Parameters(index = "0", paramLabel = "teamName", description = "Team name")
	private String teamName;

	@Override
	@NotNull
	protected Request buildRequest() {
		return new Request(teamName);
	}

	public static final class Request {
		@NotNull
		private final String teamName;

		public Request(@NotNull String teamName) {
			this.teamName = teamName;
		}

		@NotNull
		public String getTeamName() {
			return teamName;
		}
	}

}

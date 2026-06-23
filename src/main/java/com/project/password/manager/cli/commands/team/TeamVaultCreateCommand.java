package com.project.password.manager.cli.commands.team;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.cli.commands.DelegatingCliCommand;
import com.project.password.manager.cli.handlers.team.TeamVaultCreateCommandHandler;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "create", mixinStandardHelpOptions = true, description = "Creates a vault for a team owned by the authenticated user.")
public class TeamVaultCreateCommand extends DelegatingCliCommand<TeamVaultCreateCommand.Request, TeamVaultCreateCommandHandler> {

	@Parameters(index = "0", paramLabel = "teamId", description = "Team identifier")
	private String teamId;

	@Parameters(index = "1", paramLabel = "vaultName", description = "Vault display name")
	private String vaultName;

	@Override
	@NotNull
	protected Request buildRequest() {
		return new Request(teamId, vaultName);
	}

	public static final class Request {
		@NotNull
		private final String teamId;
		@NotNull
		private final String vaultName;

		public Request(@NotNull String teamId, @NotNull String vaultName) {
			this.teamId = teamId;
			this.vaultName = vaultName;
		}

		@NotNull
		public String getTeamId() {
			return teamId;
		}

		@NotNull
		public String getVaultName() {
			return vaultName;
		}
	}
}
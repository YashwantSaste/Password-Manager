package com.project.password.manager.cli.handlers.team;

import org.jetbrains.annotations.NotNull;

import com.google.inject.Inject;
import com.project.password.manager.cli.commands.team.TeamVaultCreateCommand;
import com.project.password.manager.cli.handlers.AbstractAuthorizedCommandHandler;
import com.project.password.manager.cli.runtime.CliOutput;
import com.project.password.manager.cli.runtime.CliSession;
import com.project.password.manager.cli.runtime.CliTheme;
import com.project.password.manager.middleware.RequireAuthorization;
import com.project.password.manager.service.UserService;
import com.project.password.manager.service.VaultService;

public class TeamVaultCreateCommandHandler extends AbstractAuthorizedCommandHandler<TeamVaultCreateCommand.Request> {

	@NotNull
	private final VaultService vaultService;

	@Inject
	public TeamVaultCreateCommandHandler(@NotNull CliSession session, @NotNull UserService userService,
			@NotNull VaultService vaultService, @NotNull CliOutput output) {
		super(session, userService, output);
		this.vaultService = vaultService;
	}

	@Override
	@RequireAuthorization
	public void handle(@NotNull TeamVaultCreateCommand.Request request) {
		vaultService.createTeamVault(currentUserId(), request.getTeamId(), request.getVaultName());
		output.info(CliTheme.success("Created team vault " + request.getVaultName() + " for team " + request.getTeamId()));
	}
}
package com.project.password.manager.cli.handlers.team;

import org.jetbrains.annotations.NotNull;

import com.google.inject.Inject;
import com.project.password.manager.cli.commands.team.TeamVaultListCommand;
import com.project.password.manager.cli.handlers.AbstractAuthorizedCommandHandler;
import com.project.password.manager.cli.handlers.CliViewPrinter;
import com.project.password.manager.cli.runtime.CliOutput;
import com.project.password.manager.cli.runtime.CliSession;
import com.project.password.manager.middleware.RequireAuthorization;
import com.project.password.manager.service.UserService;
import com.project.password.manager.service.VaultService;

public class TeamVaultListCommandHandler extends AbstractAuthorizedCommandHandler<TeamVaultListCommand.Request> {

	@NotNull
	private final VaultService vaultService;

	@Inject
	public TeamVaultListCommandHandler(@NotNull CliSession session, @NotNull UserService userService,
			@NotNull VaultService vaultService, @NotNull CliOutput output) {
		super(session, userService, output);
		this.vaultService = vaultService;
	}

	@Override
	@RequireAuthorization
	public void handle(@NotNull TeamVaultListCommand.Request request) {
		output.info(CliViewPrinter.formatVaults(
				vaultService.getTeamVaults(currentUserId(), request.getTeamId()), request.isShowIds()));
	}
}
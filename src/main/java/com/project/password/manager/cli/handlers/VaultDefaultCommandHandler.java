package com.project.password.manager.cli.handlers;

import org.jetbrains.annotations.NotNull;

import com.google.inject.Inject;
import com.project.password.manager.cli.commands.VaultDefaultCommand;
import com.project.password.manager.cli.runtime.CliOutput;
import com.project.password.manager.cli.runtime.CliSession;
import com.project.password.manager.middleware.RequireAuthorization;
import com.project.password.manager.service.UserService;
import com.project.password.manager.service.VaultService;

public class VaultDefaultCommandHandler extends AbstractAuthorizedCommandHandler<VaultDefaultCommand.Request> {

	@NotNull
	private final VaultService vaultService;

	@Inject
	public VaultDefaultCommandHandler(@NotNull CliSession session, @NotNull UserService userService,
			@NotNull VaultService vaultService, @NotNull CliOutput output) {
		super(session, userService, output);
		this.vaultService = vaultService;
	}

	@Override
	@RequireAuthorization
	public void handle(@NotNull VaultDefaultCommand.Request request) {
		output.info(CliViewPrinter.formatVault(vaultService.getDefaultVault(currentUserId())));
	}
}
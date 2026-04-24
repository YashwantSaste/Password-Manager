package com.project.password.manager.cli.handlers.vault;

import org.jetbrains.annotations.NotNull;

import com.google.inject.Inject;
import com.project.password.manager.cli.commands.vault.VaultCreateCommand;
import com.project.password.manager.cli.handlers.AbstractAuthorizedCommandHandler;
import com.project.password.manager.cli.runtime.CliOutput;
import com.project.password.manager.cli.runtime.CliSession;
import com.project.password.manager.middleware.RequireAuthorization;
import com.project.password.manager.service.UserService;
import com.project.password.manager.service.VaultService;

public class VaultCreateCommandHandler extends AbstractAuthorizedCommandHandler<VaultCreateCommand.Request> {

	@NotNull
	private final VaultService vaultService;

	@Inject
	public VaultCreateCommandHandler(@NotNull CliSession session, @NotNull UserService userService,
			@NotNull VaultService vaultService, @NotNull CliOutput output) {
		super(session, userService, output);
		this.vaultService = vaultService;
	}

	@Override
	@RequireAuthorization
	public void handle(@NotNull VaultCreateCommand.Request request) {
		vaultService.createVaultForUser(currentUserId(), request.getVaultName());
		output.info(com.project.password.manager.cli.runtime.CliTheme.success("Created vault " + request.getVaultName()));
	}
}
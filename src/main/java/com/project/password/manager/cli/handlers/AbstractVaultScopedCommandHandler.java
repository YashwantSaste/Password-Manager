package com.project.password.manager.cli.handlers;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.project.password.manager.cli.runtime.CliOutput;
import com.project.password.manager.cli.runtime.CliSession;
import com.project.password.manager.service.UserService;
import com.project.password.manager.service.VaultService;

public abstract class AbstractVaultScopedCommandHandler<TRequest> extends AbstractAuthorizedCommandHandler<TRequest> {

	private final VaultService vaultService;

	protected AbstractVaultScopedCommandHandler(@NotNull CliSession session, @NotNull UserService userService,
			@NotNull VaultService vaultService, @NotNull CliOutput output) {
		super(session, userService, output);
		this.vaultService = vaultService;
	}

	@NotNull
	protected final String resolveVaultId(@Nullable String vaultReference) {
		return vaultService.resolveOwnedVaultId(currentUserId(), vaultReference);
	}
}
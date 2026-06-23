package com.project.password.manager.cli.handlers;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.project.password.manager.cli.runtime.CliOutput;
import com.project.password.manager.cli.runtime.CliSession;
import com.project.password.manager.model.IVault;
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
		return vaultService.resolveVaultIdAccessibleToUser(currentUserId(), vaultReference);
	}

	@NotNull
	protected final List<String> resolveVaultIds(@Nullable String vaultReference) {
		if (vaultReference != null && !vaultReference.trim().isEmpty()) {
			return List.of(resolveVaultId(vaultReference));
		}
		List<String> vaultIds = new ArrayList<>();
		for (IVault vault : vaultService.getAllVaults(currentUserId())) {
			vaultIds.add(vault.getId());
		}
		return vaultIds;
	}
}
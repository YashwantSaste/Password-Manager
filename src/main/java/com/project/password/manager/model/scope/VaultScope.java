package com.project.password.manager.model.scope;

import com.project.password.manager.guice.GuicePlatform;
import com.project.password.manager.model.IEntity;
import com.project.password.manager.service.VaultService;

import jakarta.validation.constraints.NotNull;

public class VaultScope implements IScope {

	@NotNull
	private String vaultId;

	public VaultScope() {
		this.vaultId = "";
	}

	public VaultScope(@NotNull String vaultId) {
		this.vaultId = vaultId;
	}

	@Override
	@NotNull
	public ScopeType getType() {
		return ScopeType.VAULT;
	}

	@Override
	@NotNull
	public String getScopeId() {
		return vaultId;
	}

	@Override
	@NotNull
	public IEntity getScopedEntity() {
		VaultService vaultService = GuicePlatform.getInstance(VaultService.class);
		return vaultService.getVault(vaultId);
	}

}

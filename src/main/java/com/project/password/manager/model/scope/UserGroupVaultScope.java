package com.project.password.manager.model.scope;

import com.project.password.manager.model.IEntity;

import jakarta.validation.constraints.NotNull;

public class UserGroupVaultScope implements IScope {

	@NotNull
	private String vaultId;

	public UserGroupVaultScope() {
		this.vaultId = "";
	}

	public UserGroupVaultScope(@NotNull String vaultId) {
		this.vaultId = vaultId;
	}

	@Override
	@NotNull
	public ScopeType getType() {
		return ScopeType.USER_GROUP_VAULT;
	}

	@Override
	@NotNull
	public String getScopeId() {
		return vaultId;
	}

	@Override
	@NotNull
	public IEntity getScopedEntity() {
		throw new IllegalArgumentException("Scoped vaults are not present for User Group");
	}

}

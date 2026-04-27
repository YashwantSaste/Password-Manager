package com.project.password.manager.model;

import org.jetbrains.annotations.NotNull;

public interface IVault extends HasMetadata {
	@NotNull
	String getId();

	@NotNull
	String getName();

	@NotNull
	VaultScope getScope();

	@NotNull
	String getScopeId();

	@NotNull
	String getEncryptedBlob();

	void setId(@NotNull String vaultId);

	void setName(@NotNull String vaultName);

	void setScope(@NotNull VaultScope scope);

	void setScopeId(@NotNull String scopeId);

	void setEncryptedBlob(@NotNull String encryptedBlob);

}

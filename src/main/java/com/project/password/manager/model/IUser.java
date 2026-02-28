package com.project.password.manager.model;

import java.util.List;

import org.jetbrains.annotations.NotNull;

public interface IUser extends IEntity {
	@NotNull
	String getId();

	@NotNull
	String getAuthVerifier();

	@NotNull
	String getKeySalt();

	@NotNull
	String getDefaultVaultId();

	@NotNull
	List<IVault> getVaults();

	@NotNull
	IMetadata metadata();

	void setId(@NotNull String id);

	void setAuthVerifier(@NotNull String authVerifier);

	void setKeySalt(@NotNull String keySalt);

	void setDefaultVaultId(@NotNull String defaultVaultId);

	void setVaults(@NotNull List<IVault> vaults);

	void setMetadata(@NotNull IMetadata metadata);
}

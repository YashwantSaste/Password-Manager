package com.project.password.manager.model;

import org.jetbrains.annotations.NotNull;

public interface IVault extends IEntity {
	@NotNull
	String getId();

	@NotNull
	String getName();

	@NotNull
	String getUserId();

	@NotNull
	String getEncryptedBlob();

	@NotNull
	IMetadata metadata();

	void setId(@NotNull String vaultId);

	void setName(@NotNull String vaultName);

	void setUserId(@NotNull String userId);

	void setEncryptedBlob(@NotNull String encryptedBlob);

	void setMetadata(@NotNull IMetadata metadata);

}

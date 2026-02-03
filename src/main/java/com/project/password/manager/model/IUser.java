package com.project.password.manager.model;

import java.time.LocalDateTime;
import java.util.List;

import org.jetbrains.annotations.NotNull;

public interface IUser extends IBase {
	@NotNull
	String getId();

	@NotNull
	String getName();

	@NotNull
	String getAuthVerifier();

	@NotNull
	String getSalt();

	@NotNull
	String getDefaultVaultID();

	@NotNull
	List<IVault> getVaults();

	@NotNull
	LocalDateTime createdAt();

	@NotNull
	LocalDateTime updatedAt();
}

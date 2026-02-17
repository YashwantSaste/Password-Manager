package com.project.password.manager.model;

import java.util.List;

import org.jetbrains.annotations.NotNull;

public interface IUser extends IEntity {
	@NotNull
	String getId();

	@NotNull
	String getName();

	@NotNull
	String getAuthVerifier();

	@NotNull
	String getKeySalt();

	@NotNull
	String getDefaultVaultId();

	@NotNull
	List<IVault> getVaults();

}

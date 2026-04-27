package com.project.password.manager.model;

import java.util.List;

import org.jetbrains.annotations.NotNull;

public interface ITeam extends HasMetadata {

	@NotNull
	String getId();

	@NotNull
	String name();

	@NotNull
	List<IUser> owners();

	@NotNull
	List<IUser> memebers();

	@NotNull
	String getDefaultVaultId();

	@NotNull
	String getKeySalt();

	void setId(@NotNull String id);

	void setName(@NotNull String name);

	void setOwners(@NotNull List<IUser> owners);

	void setMembers(@NotNull List<IUser> members);

	void setDefaultVaultId(@NotNull String defaultVaultId);

	void setKeySalt(@NotNull String keySalt);
}

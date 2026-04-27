package com.project.password.manager.model;

import java.util.List;

import org.jetbrains.annotations.NotNull;

public interface ITeam extends HasMetadata {

	@NotNull
	String id();

	@NotNull
	String name();

	@NotNull
	List<IUser> owners();

	@NotNull
	List<IUser> memebers();

	@NotNull
	IVault defaultVault();

	@NotNull
	List<IVault> allVaults();

	void setId(@NotNull String id);

	void setName(@NotNull String name);

	void setOwners(@NotNull List<IUser> owners);

	void setMembers(@NotNull List<IUser> members);

	void setDefaultVault(@NotNull IVault defaultVault);

	void setAllVaults(@NotNull List<IVault> allVaults);
}

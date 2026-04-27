package com.project.password.manager.model.database.file.storage;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.model.IMetadata;
import com.project.password.manager.model.ITeam;
import com.project.password.manager.model.IUser;
import com.project.password.manager.model.IVault;

public class Team implements ITeam, IFileStorableEntity {

	private String id;
	private String name;
	private List<IUser> owners;
	private List<IUser> members;
	private IMetadata metadata;
	private IVault defaultVault;
	private List<IVault> allVaults;

	public Team(String id, String name, List<IUser> owners, List<IUser> members, IVault defaultVault,
			List<IVault> allVaults) {
		this.id = id;
		this.name = name;
		this.owners = owners;
		this.members = members;
		this.defaultVault = defaultVault;
		this.allVaults = allVaults;
	}

	@Override
	@NotNull
	public IMetadata metadata() {
		return metadata;
	}

	@Override
	public void setMetadata(@NotNull IMetadata metadata) {
		this.metadata = metadata;
	}

	@Override
	@NotNull
	public String getId() {
		return id;
	}

	@Override
	@NotNull
	public String name() {
		return name;
	}

	@Override
	@NotNull
	public List<IUser> owners() {
		if (owners == null) {
			owners = new ArrayList<>();
		}
		return owners;
	}

	@Override
	@NotNull
	public List<IUser> memebers() {
		if (members == null) {
			members = new ArrayList<>();
		}
		return members;
	}

	@Override
	@NotNull
	public IVault defaultVault() {
		return defaultVault;
	}

	@Override
	@NotNull
	public List<IVault> allVaults() {
		if (allVaults == null) {
			owners = new ArrayList<>();
		}
		return allVaults;
	}

	@Override
	public void setId(@NotNull String id) {
		this.id = id;
	}

	@Override
	public void setName(@NotNull String name) {
		this.name = name;
	}

	@Override
	public void setOwners(@NotNull List<IUser> owners) {
		this.owners = owners;
	}

	@Override
	public void setMembers(@NotNull List<IUser> members) {
		this.members = members;
	}

	@Override
	public void setDefaultVault(@NotNull IVault defaultVault) {
		this.defaultVault = defaultVault;
	}

	@Override
	public void setAllVaults(@NotNull List<IVault> allVaults) {
		this.allVaults = allVaults;
	}


	@Override
	@NotNull
	public String getFileName() {
		return "team.json";
	}
}

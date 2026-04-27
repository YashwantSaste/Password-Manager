package com.project.password.manager.model.database.file.storage;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.password.manager.model.IMetadata;
import com.project.password.manager.model.ITeam;
import com.project.password.manager.model.IUser;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Team implements ITeam, IFileStorableEntity {

	private String id;
	private String name;
	private List<IUser> owners;
	private List<IUser> members;
	private IMetadata metadata = new Metadata();
	private String defaultVaultId;
	private String keySalt;

	public Team() {
		// for jackson
	}

	public Team(String id, String name, List<IUser> owners, List<IUser> members, String defaultVaultId,
			String keySalt) {
		this.id = id;
		this.name = name;
		this.owners = owners;
		this.members = members;
		this.defaultVaultId = defaultVaultId;
		this.keySalt = keySalt;
	}

	@Override
	@NotNull
	public IMetadata metadata() {
		if (metadata == null) {
			metadata = new Metadata();
		}
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
	public String getDefaultVaultId() {
		return defaultVaultId != null ? defaultVaultId : "";
	}

	@Override
	@NotNull
	public String getKeySalt() {
		return keySalt != null ? keySalt : "";
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
	public void setDefaultVaultId(@NotNull String defaultVaultId) {
		this.defaultVaultId = defaultVaultId;
	}

	@Override
	public void setKeySalt(@NotNull String keySalt) {
		this.keySalt = keySalt;
	}

	@Override
	@NotNull
	public String getFileName() {
		return "team.json";
	}
}

package com.project.password.manager.model.database.file.storage;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.password.manager.model.IMetadata;
import com.project.password.manager.model.IUser;
import com.project.password.manager.model.IVault;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements IUser, IFileStorableEntity {

	private String id;
	private String name;
	private String authVerifier;
	private String keySalt;
	private String defaultVaultId;
	private List<IVault> vaults;
	private IMetadata metadata;

	public User() {
		// for jackson
	}

	public User(String id, String name, String authVerifier, String keySalt, String defaultVaultId,
			List<IVault> vaults, IMetadata metadata) {
		this.id = id;
		this.name = name;
		this.authVerifier = authVerifier;
		this.keySalt = keySalt;
		this.defaultVaultId = defaultVaultId;
		this.vaults = vaults;
		this.metadata = metadata;
	}

	@Override
	@NotNull
	public String getId() {
		return id;
	}

	@Override
	@NotNull
	public String getName() {
		return name;
	}

	@Override
	@NotNull
	public String getAuthVerifier() {
		return authVerifier;
	}

	@Override
	@NotNull
	public String getDefaultVaultId() {
		return defaultVaultId;
	}

	@Override
	@NotNull
	public List<IVault> getVaults() {
		if (vaults == null) {
			vaults = new ArrayList<>();
		}
		return vaults;
	}

	@Override
	@NotNull
	public IMetadata metadata() {
		return metadata;
	}

	@Override
	@NotNull
	public String getKeySalt() {
		return keySalt;
	}

	@Override
	@NotNull
	public String getFileName() {
		return "user.json";
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
	public void setAuthVerifier(@NotNull String authVerifier) {
		this.authVerifier = authVerifier;
	}

	@Override
	public void setKeySalt(@NotNull String keySalt) {
		this.keySalt = keySalt;
	}

	@Override
	public void setDefaultVaultId(@NotNull String defaultVaultId) {
		this.defaultVaultId = defaultVaultId;
	}

	@Override
	public void setVaults(@NotNull List<IVault> vaults) {
		this.vaults = vaults;
	}

	@Override
	public void setMetadata(@NotNull IMetadata metadata) {
		this.metadata = metadata;
	}
}

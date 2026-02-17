package com.project.password.manager.model.database.file.storage;

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

}

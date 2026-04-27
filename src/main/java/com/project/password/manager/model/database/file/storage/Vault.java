package com.project.password.manager.model.database.file.storage;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.model.IMetadata;
import com.project.password.manager.model.IVault;
import com.project.password.manager.model.VaultScope;

public class Vault implements IVault, IFileStorableEntity {

	private String id;
	private String name;
	private VaultScope scope = VaultScope.USER;
	private String scopeId;
	private String encryptedBlob;
	private IMetadata metadata = new Metadata();
	private String fileName;

	public Vault() {
		// for jackson
	}

	public Vault(@NotNull String id, @NotNull String name, @NotNull VaultScope scope, @NotNull String scopeId,
			@NotNull String encyrptedBlob) {
		this.id = id;
		this.name = name;
		this.scope = scope;
		this.scopeId = scopeId;
		this.encryptedBlob = encyrptedBlob;
	}

	@Override
	@NotNull
	public String getId() {
		return id;
	}

	@Override
	@NotNull
	public String getName() {
		return name != null ? name : "";
	}

	@Override
	@NotNull
	public VaultScope getScope() {
		return scope != null ? scope : VaultScope.USER;
	}

	@Override
	@NotNull
	public String getScopeId() {
		return scopeId != null ? scopeId : "";
	}

	@Override
	@NotNull
	public String getEncryptedBlob() {
		return encryptedBlob != null ? encryptedBlob : "";
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
	public @NotNull String getFileName() {
		// getId() --> Jackson reads the id via a public function as it does not have
		// access to core values
		this.fileName = getId() + ".json";
		return fileName;
	}

	@Override
	public void setId(@NotNull String vaultId) {
		this.id = vaultId;
	}

	@Override
	public void setName(@NotNull String vaultName) {
		this.name = vaultName;
	}

	@Override
	public void setScope(@NotNull VaultScope scope) {
		this.scope = scope;
	}

	@Override
	public void setScopeId(@NotNull String scopeId) {
		this.scopeId = scopeId;
	}

	@Override
	public void setEncryptedBlob(@NotNull String encryptedBlob) {
		this.encryptedBlob = encryptedBlob;
	}

	@Override
	public void setMetadata(@NotNull IMetadata metadata) {
		this.metadata = metadata != null ? metadata : new Metadata();
	}

}

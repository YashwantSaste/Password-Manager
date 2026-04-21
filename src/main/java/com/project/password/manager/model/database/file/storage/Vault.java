package com.project.password.manager.model.database.file.storage;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.model.IMetadata;
import com.project.password.manager.model.IVault;

public class Vault implements IVault, IFileStorableEntity {

	private String id;
	private String userId;
	private String encryptedBlob;
	private IMetadata metadata;
	private String fileName;

	public Vault() {
		// for jackson
	}

	public Vault(@NotNull String id, @NotNull String userId, @NotNull String encyrptedBlob) {
		this.id = id;
		this.userId = userId;
		this.encryptedBlob = encyrptedBlob;
	}

	@Override
	@NotNull
	public String getId() {
		return id;
	}

	@Override
	@NotNull
	public String getUserId() {
		return userId;
	}

	@Override
	@NotNull
	public String getEncryptedBlob() {
		return encryptedBlob != null ? encryptedBlob : "";
	}

	@Override
	@NotNull
	public IMetadata metadata() {
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
	public void setUserId(@NotNull String userId) {
		this.userId = userId;
	}

	@Override
	public void setEncryptedBlob(@NotNull String encryptedBlob) {
		this.encryptedBlob = encryptedBlob;
	}

	@Override
	public void setMetadata(@NotNull IMetadata metadata) {
		this.metadata = metadata;
	}

}

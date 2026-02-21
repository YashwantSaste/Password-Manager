package com.project.password.manager.model.database.file.storage;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.model.IMetadata;
import com.project.password.manager.model.IVault;

public class Vault implements IVault, IFileStorableEntity {

	private String id;
	private String userId;

	public Vault(@NotNull String id, @NotNull String userId, @NotNull String encyrptedBlob) {
		this.id = id;
		this.userId = userId;
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
	public @NotNull String getEncryptedBlob() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @NotNull IMetadata metadata() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @NotNull String getFileName() {
		return id + ".json";
	}

}

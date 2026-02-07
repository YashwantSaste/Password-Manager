package com.project.password.manager.model.database.file.storage;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.model.IMetadata;
import com.project.password.manager.model.IVault;

public class Vault implements IVault, IFileStorableEntity {

	private String id;

	@Override
	public @NotNull String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @NotNull String getUserId() {
		// TODO Auto-generated method stub
		return null;
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

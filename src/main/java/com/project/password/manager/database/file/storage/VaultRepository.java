package com.project.password.manager.database.file.storage;

import java.io.File;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.model.database.file.storage.Vault;

public class VaultRepository extends FileStorageRepository<Vault, String> {

	private static final String VAULTS_WORKSPACE_FOLDER = "vaults";

	public VaultRepository(@NotNull File workspace) {
		super(new File(workspace, VAULTS_WORKSPACE_FOLDER));
	}

	@Override
	@NotNull
	protected File resolveEntityDirectoryInFileSystem(@NotNull String id) {
		return new File(workspace, id);
	}

	@Override
	@NotNull
	protected Class<Vault> getEntityClass() {
		return Vault.class;
	}

	@Override
	@NotNull
	protected String getEntityFileName() {
		// vault has no specific file naming standard
		return "";
	}

}

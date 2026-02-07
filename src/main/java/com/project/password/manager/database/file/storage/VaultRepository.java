package com.project.password.manager.database.file.storage;

import java.io.File;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.model.database.file.storage.Vault;

public class VaultRepository extends FileStorageRepository<Vault, String> {

	@NotNull
	private final String userId;

	protected VaultRepository(@NotNull File workspace, @NotNull String userId) {
		super(new File(workspace, "users/" + userId + "/vaults"));
		this.userId = userId;
	}

	@Override
	@NotNull
	protected File resolveEntityDirectoryInFileSystem(@NotNull String id) {
		return workspace;
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

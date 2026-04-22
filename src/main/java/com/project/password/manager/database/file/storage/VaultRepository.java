package com.project.password.manager.database.file.storage;

import java.io.File;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
	@Nullable
	public Vault findById(@NotNull String id) {
		File entityDir = resolveEntityDirectoryInFileSystem(id);
		if (!entityDir.exists()) {
			return null;
		}
		File entityFile = new File(entityDir, id + ".json");
		fileManager = new FileManager<>(entityFile, getEntityClass());
		return fileManager.readFromFile();
	}

	@Override
	@NotNull
	protected String getEntityFileName() {
		return "vault.json";
	}

}

package com.project.password.manager.database.file.storage;

import java.util.ArrayList;
import java.io.File;
import java.util.List;

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
	public List<Vault> findAll() {
		List<Vault> vaults = new ArrayList<>();
		if (!workspace.exists()) {
			return vaults;
		}
		File[] vaultDirectories = workspace.listFiles(File::isDirectory);
		if (vaultDirectories == null) {
			return vaults;
		}
		for (File vaultDirectory : vaultDirectories) {
			File entityFile = new File(vaultDirectory, vaultDirectory.getName() + ".json");
			if (!entityFile.exists()) {
				continue;
			}
			FileManager<Vault> currentFileManager = new FileManager<>(entityFile, getEntityClass());
			Vault vault = currentFileManager.readFromFile();
			if (vault != null) {
				vaults.add(vault);
			}
		}
		return vaults;
	}

	@Override
	@NotNull
	protected String getEntityFileName() {
		return "vault.json";
	}

}

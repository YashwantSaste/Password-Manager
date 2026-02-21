package com.project.password.manager.database.file.storage;

import java.io.File;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.project.password.manager.database.DataRepository;
import com.project.password.manager.model.database.file.storage.IFileStorableEntity;
import com.project.password.manager.util.Logger;

public abstract class FileStorageRepository<T extends IFileStorableEntity, Id> implements DataRepository<T, Id> {

	private static final Logger log = Logger.getLogger("FileRepository class");
	@NotNull
	protected final File workspace;
	@NotNull
	protected FileManager<T> fileManager;

	protected FileStorageRepository(@NotNull File workspace) {
		this.workspace = workspace;
	}

	@Override
	public void save(@NotNull T entity) {
		if (entity.getId() == null) {
			throw new IllegalStateException("Entity ID cannot be null before saving.");
		}
		File entityDirectory = resolveEntityDirectoryInFileSystem(entity.getId());
		entityDirectory.mkdirs();
		File entityFile = new File(entityDirectory, entity.getFileName());
		fileManager = new FileManager<>(entityFile, getEntityClass());
		if (!fileManager.doFileExist()) {
			fileManager.writeToFile(entity);
		} else {
			log.warn("Given file already exists in the workspace: " + entityFile.getAbsolutePath());
		}
	}

	@Override
	@Nullable
	public T findById(@NotNull Id id) {
		File entityDir = resolveEntityDirectoryInFileSystem(id.toString());
		if (!entityDir.exists()) {
			log.debug("File related to related ID does not exist in the workspace");
			return null;
		}
		File entityFile = new File(entityDir, getEntityFileName());
		fileManager = new FileManager<T>(entityFile, getEntityClass());
		return fileManager.readFromFile();
	}

	@Override
	public void delete(@NotNull T entity) {
		// TODO Add delete logic
	}

	@Override
	public void update(@NotNull Id id, @NotNull T entity) {
		// TODO Add update logic
	}

	@NotNull
	protected abstract File resolveEntityDirectoryInFileSystem(@NotNull String id);

	@NotNull
	protected abstract Class<T> getEntityClass();

	@NotNull
	protected abstract String getEntityFileName();
}

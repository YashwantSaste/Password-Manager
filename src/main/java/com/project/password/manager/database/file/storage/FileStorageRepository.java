package com.project.password.manager.database.file.storage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.project.password.manager.database.DataRepository;
import com.project.password.manager.logging.ITransactionLogger;
import com.project.password.manager.model.database.file.storage.IFileStorableEntity;
import com.project.password.manager.util.Logger;
import com.project.password.manager.util.MetadataListener;

public abstract class FileStorageRepository<T extends IFileStorableEntity, Id> implements DataRepository<T, Id> {

	private static final Logger log = Logger.getLogger("FileRepository class");
	@NotNull
	protected final File workspace;
	@NotNull
	protected final ITransactionLogger transactionLogger;
	@NotNull
	protected FileManager<T> fileManager;

	protected FileStorageRepository(@NotNull File workspace, @NotNull ITransactionLogger transactionLogger) {
		this.workspace = workspace;
		this.transactionLogger = transactionLogger;
	}

	@Override
	public void save(@NotNull T entity) {
		if (entity.getId() == null) {
			throw new IllegalStateException("Entity ID cannot be null before saving.");
		}
		MetadataListener.beforeCreate(entity);
		File entityDirectory = resolveEntityDirectoryInFileSystem(entity.getId());
		entityDirectory.mkdirs();
		File entityFile = new File(entityDirectory, entity.getFileName());
		fileManager = new FileManager<>(entityFile, getEntityClass());
		if (!fileManager.doFileExist()) {
			fileManager.writeToFile(entity);
			logRepositoryOperation("save", entity.getClass().getSimpleName(), entity.getId(), "SUCCESS",
					entityFile.getAbsolutePath());
		} else {
			logRepositoryOperation("save", entity.getClass().getSimpleName(), entity.getId(), "SKIPPED",
					"Entity file already exists at " + entityFile.getAbsolutePath());
			log.warn("Given file already exists in the workspace: " + entityFile.getAbsolutePath());
		}
	}

	@Override
	@Nullable
	public T findById(@NotNull Id id) {
		File entityDir = resolveEntityDirectoryInFileSystem(id.toString());
		if (!entityDir.exists()) {
			log.debug("File related to related ID does not exist in the workspace");
			logRepositoryOperation("findById", getEntityClass().getSimpleName(), id.toString(), "MISS", null);
			return null;
		}
		File entityFile = new File(entityDir, getEntityFileName());
		fileManager = new FileManager<T>(entityFile, getEntityClass());
		T entity = fileManager.readFromFile();
		logRepositoryOperation("findById", getEntityClass().getSimpleName(), id.toString(),
				entity == null ? "MISS" : "SUCCESS", entityFile.getAbsolutePath());
		return entity;
	}

	@Override
	@NotNull
	public List<T> findAll() {
		List<T> entities = new ArrayList<>();
		if (!workspace.exists()) {
			return entities;
		}
		File[] entityDirectories = workspace.listFiles(File::isDirectory);
		if (entityDirectories == null) {
			return entities;
		}
		for (File entityDirectory : entityDirectories) {
			File entityFile = new File(entityDirectory, getEntityFileName());
			if (!entityFile.exists()) {
				continue;
			}
			FileManager<T> currentFileManager = new FileManager<>(entityFile, getEntityClass());
			T entity = currentFileManager.readFromFile();
			if (entity != null) {
				entities.add(entity);
			}
		}
		logRepositoryOperation("findAll", getEntityClass().getSimpleName(), null, "SUCCESS",
				"count=" + entities.size());
		return entities;
	}

	@Override
	public void delete(@NotNull Id id) {
		File entityDirectory = resolveEntityDirectoryInFileSystem(id.toString());
		File entityFile = new File(entityDirectory, getEntityFileName());
		if (entityFile.exists()) {
			entityFile.delete();
			logRepositoryOperation("delete", getEntityClass().getSimpleName(), id.toString(), "SUCCESS",
					entityFile.getAbsolutePath());
			return;
		}
		logRepositoryOperation("delete", getEntityClass().getSimpleName(), id.toString(), "MISS",
				entityFile.getAbsolutePath());
	}

	@Override
	public void update(@NotNull Id id, @NotNull T entity) {
		if (entity.getId() == null) {
			throw new IllegalStateException("Entity ID cannot be null before updating.");
		}
		MetadataListener.beforeUpdate(entity);
		File entityDirectory = resolveEntityDirectoryInFileSystem(id.toString());
		entityDirectory.mkdirs();
		File entityFile = new File(entityDirectory, entity.getFileName());
		fileManager = new FileManager<>(entityFile, getEntityClass());
		fileManager.writeToFile(entity);
		logRepositoryOperation("update", entity.getClass().getSimpleName(), entity.getId(), "SUCCESS",
				entityFile.getAbsolutePath());
	}

	protected void logRepositoryOperation(@NotNull String operation, @NotNull String entityType,
			@Nullable String entityId, @NotNull String status, @Nullable String details) {
		transactionLogger.logRepositoryOperation(false, getClass().getSimpleName(), operation, entityType, entityId,
				status, details);
	}

	@NotNull
	protected abstract File resolveEntityDirectoryInFileSystem(@NotNull String id);

	@NotNull
	protected abstract Class<T> getEntityClass();

	@NotNull
	protected abstract String getEntityFileName();
}

package com.project.password.manager.database.file.storage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.project.password.manager.database.EntryDataRepository;
import com.project.password.manager.database.EntryStorageKey;
import com.project.password.manager.logging.ITransactionLogger;
import com.project.password.manager.model.entry.EncryptedEntryRecord;

public class FileEntryRepository implements EntryDataRepository {

	private static final String ENTRIES_WORKSPACE_FOLDER = "entries";
	@NotNull
	private final File workspace;
	@NotNull
	private final ITransactionLogger transactionLogger;

	public FileEntryRepository(@NotNull File workspace, @NotNull ITransactionLogger transactionLogger) {
		this.workspace = new File(workspace, ENTRIES_WORKSPACE_FOLDER);
		this.transactionLogger = transactionLogger;
	}

	@Override
	public void save(@NotNull EncryptedEntryRecord record) {
		File entryFile = resolveEntryFile(record.getVaultId(), record.getId());
		entryFile.getParentFile().mkdirs();
		new FileManager<>(entryFile, EncryptedEntryRecord.class).writeToFile(record);
		logRepositoryOperation("save", record.getId(), "SUCCESS", entryFile.getAbsolutePath());
	}

	@Override
	@Nullable
	public EncryptedEntryRecord findById(@NotNull EntryStorageKey key) {
		File entryFile = resolveEntryFile(key.vaultId(), key.entryId());
		if (!entryFile.exists()) {
			logRepositoryOperation("findById", key.entryId(), "MISS", entryFile.getAbsolutePath());
			return null;
		}
		EncryptedEntryRecord record = new FileManager<>(entryFile, EncryptedEntryRecord.class).readFromFile();
		logRepositoryOperation("findById", key.entryId(), record == null ? "MISS" : "SUCCESS",
				entryFile.getAbsolutePath());
		return record;
	}

	@Override
	@NotNull
	public List<EncryptedEntryRecord> findAll() {
		List<EncryptedEntryRecord> entries = new ArrayList<>();
		File[] vaultDirectories = workspace.listFiles(File::isDirectory);
		if (vaultDirectories == null) {
			return entries;
		}
		for (File vaultDirectory : vaultDirectories) {
			File[] entryFiles = vaultDirectory.listFiles((dir, name) -> name.endsWith(".json"));
			if (entryFiles == null) {
				continue;
			}
			for (File entryFile : entryFiles) {
				EncryptedEntryRecord entry = new FileManager<>(entryFile, EncryptedEntryRecord.class).readFromFile();
				if (entry != null) {
					entries.add(entry);
				}
			}
		}
		logRepositoryOperation("findAll", null, "SUCCESS", "count=" + entries.size());
		return entries;
	}

	@Override
	@NotNull
	public List<EncryptedEntryRecord> findByVaultId(@NotNull String vaultId) {
		List<EncryptedEntryRecord> entries = new ArrayList<>();
		File vaultDirectory = new File(workspace, vaultId);
		File[] entryFiles = vaultDirectory.listFiles((dir, name) -> name.endsWith(".json"));
		if (entryFiles == null) {
			return entries;
		}
		for (File entryFile : entryFiles) {
			EncryptedEntryRecord entry = new FileManager<>(entryFile, EncryptedEntryRecord.class).readFromFile();
			if (entry != null) {
				entries.add(entry);
			}
		}
		logRepositoryOperation("findByVaultId", vaultId, "SUCCESS", "count=" + entries.size());
		return entries;
	}

	@Override
	public void delete(@NotNull EntryStorageKey key) {
		deleteByKey(key);
	}

	@Override
	public void update(@NotNull EntryStorageKey key, @NotNull EncryptedEntryRecord entity) {
		save(entity);
	}

	public void deleteByKey(@NotNull EntryStorageKey key) {
		File entryFile = resolveEntryFile(key.vaultId(), key.entryId());
		if (entryFile.exists()) {
			entryFile.delete();
			logRepositoryOperation("delete", key.entryId(), "SUCCESS", entryFile.getAbsolutePath());
			return;
		}
		logRepositoryOperation("delete", key.entryId(), "MISS", entryFile.getAbsolutePath());
	}

	@NotNull
	private File resolveEntryFile(@NotNull String vaultId, @NotNull String entryId) {
		return new File(new File(workspace, vaultId), entryId + ".json");
	}

	private void logRepositoryOperation(@NotNull String operation, @Nullable String entityId, @NotNull String status,
			@Nullable String details) {
		transactionLogger.logRepositoryOperation(false, getClass().getSimpleName(), operation,
				EncryptedEntryRecord.class.getSimpleName(), entityId, status, details);
	}

}
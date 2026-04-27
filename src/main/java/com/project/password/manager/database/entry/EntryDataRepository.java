package com.project.password.manager.database.entry;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.database.DataRepository;
import com.project.password.manager.model.entry.EncryptedEntryRecord;

public interface EntryDataRepository extends DataRepository<EncryptedEntryRecord, EntryStorageKey> {

	@NotNull
	List<EncryptedEntryRecord> findByVaultId(@NotNull String vaultId);

}
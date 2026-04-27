package com.project.password.manager.database.entry;

import org.jetbrains.annotations.NotNull;

public class EntryStorageKey {

	@NotNull
	private final String vaultId;
	@NotNull
	private final String entryId;

	public EntryStorageKey(@NotNull String vaultId, @NotNull String entryId) {
		this.vaultId = vaultId;
		this.entryId = entryId;
	}

	@NotNull
	public String vaultId() {
		return vaultId;
	}

	@NotNull
	public String entryId() {
		return entryId;
	}

}
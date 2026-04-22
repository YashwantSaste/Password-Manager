package com.project.password.manager.model;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;

public class VaultPayload {

	@NotNull
	private List<IEntry> entries = new ArrayList<>();

	@NotNull
	public List<IEntry> getEntries() {
		if (entries == null) {
			entries = new ArrayList<>();
		}
		return entries;
	}

	public void setEntries(@NotNull List<IEntry> entries) {
		this.entries = entries;
	}
}

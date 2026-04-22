package com.project.password.manager.cli.handlers;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.project.password.manager.model.IUser;
import com.project.password.manager.model.IVault;
import com.project.password.manager.model.entry.EntryView;
import com.project.password.manager.model.entry.NoteValue;
import com.project.password.manager.model.entry.TagValue;

public final class CliViewPrinter {

	private CliViewPrinter() {
	}

	@NotNull
	public static String formatUser(@NotNull IUser user) {
		return "userId=" + user.getId() + System.lineSeparator()
				+ "name=" + user.getName() + System.lineSeparator()
				+ "defaultVaultId=" + user.getDefaultVaultId() + System.lineSeparator()
				+ "vaultCount=" + user.getVaults().size();
	}

	@NotNull
	public static String formatVault(@NotNull IVault vault) {
		return "vaultName=" + vault.getName() + System.lineSeparator()
				+ "vaultId=" + vault.getId() + System.lineSeparator()
				+ "userId=" + vault.getUserId();
	}

	@NotNull
	public static String formatVaults(@NotNull List<IVault> vaults) {
		if (vaults.isEmpty()) {
			return "No vaults found.";
		}
		StringBuilder builder = new StringBuilder();
		for (IVault vault : vaults) {
			appendSection(builder, formatVault(vault));
		}
		return builder.toString();
	}

	@NotNull
	public static String formatEntry(@NotNull EntryView entry) {
		StringBuilder builder = new StringBuilder();
		builder.append("entryId=").append(entry.getId()).append(System.lineSeparator());
		builder.append("vaultId=").append(entry.getVaultId()).append(System.lineSeparator());
		builder.append("label=").append(entry.getLabel()).append(System.lineSeparator());
		builder.append("password=").append(entry.getPassword()).append(System.lineSeparator());
		builder.append("username=").append(valueOrDash(entry.getUsername())).append(System.lineSeparator());
		builder.append("loginName=").append(valueOrDash(entry.getLoginName())).append(System.lineSeparator());
		builder.append("url=").append(valueOrDash(entry.getUrl())).append(System.lineSeparator());
		builder.append("tags=").append(joinTags(entry.getTags())).append(System.lineSeparator());
		builder.append("notes=").append(joinNotes(entry.getNotes())).append(System.lineSeparator());
		builder.append("createdAtEpochMs=").append(entry.getCreatedAtEpochMs()).append(System.lineSeparator());
		builder.append("updatedAtEpochMs=").append(entry.getUpdatedAtEpochMs());
		return builder.toString();
	}

	@NotNull
	public static String formatEntries(@NotNull List<EntryView> entries) {
		if (entries.isEmpty()) {
			return "No entries found.";
		}
		StringBuilder builder = new StringBuilder();
		for (EntryView entry : entries) {
			appendSection(builder, formatEntry(entry));
		}
		return builder.toString();
	}

	private static void appendSection(@NotNull StringBuilder builder, @NotNull String section) {
		if (builder.length() > 0) {
			builder.append(System.lineSeparator()).append(System.lineSeparator());
		}
		builder.append(section);
	}

	@NotNull
	private static String valueOrDash(@Nullable String value) {
		return value == null || value.isBlank() ? "-" : value;
	}

	@NotNull
	private static String joinTags(@NotNull List<TagValue> tags) {
		if (tags.isEmpty()) {
			return "-";
		}
		StringBuilder builder = new StringBuilder();
		for (TagValue tag : tags) {
			if (builder.length() > 0) {
				builder.append(", ");
			}
			builder.append(tag.getValue());
		}
		return builder.toString();
	}

	@NotNull
	private static String joinNotes(@NotNull List<NoteValue> notes) {
		if (notes.isEmpty()) {
			return "-";
		}
		StringBuilder builder = new StringBuilder();
		for (NoteValue note : notes) {
			if (builder.length() > 0) {
				builder.append(" | ");
			}
			builder.append(note.getDescription());
		}
		return builder.toString();
	}
}
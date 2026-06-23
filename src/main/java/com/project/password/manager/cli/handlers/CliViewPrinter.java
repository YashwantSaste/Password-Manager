package com.project.password.manager.cli.handlers;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.project.password.manager.cli.runtime.CliTheme;
import com.project.password.manager.model.ITeam;
import com.project.password.manager.model.IUser;
import com.project.password.manager.model.UserRole;
import com.project.password.manager.model.IVault;
import com.project.password.manager.model.VaultScope;
import com.project.password.manager.model.entry.EntryView;
import com.project.password.manager.model.entry.NoteValue;
import com.project.password.manager.model.entry.TagValue;

public final class CliViewPrinter {

	private static final int KEY_WIDTH = 12;

	private CliViewPrinter() {
	}

	@NotNull
	public static String formatUser(@NotNull IUser user) {
		return section(
				CliTheme.badge("user") + "  " + CliTheme.title(user.getName()),
				field("user", user.getName()),
				field("roles", joinRoles(user.getRoles())),
				field("vaults", String.valueOf(user.getVaults().size())),
				field("default vault", valueOrDash(user.getDefaultVaultId())));
	}

	@NotNull
	public static String formatVault(@NotNull IVault vault) {
		return formatVault(vault, false);
	}

	@NotNull
	public static String formatVault(@NotNull IVault vault, boolean showIds) {
		String content = section(
				CliTheme.badge("vault") + "  " + CliTheme.title(vault.getName()),
				field(vault.getScope() == VaultScope.TEAM ? "team" : "owner", vault.getScopeId()));
		if (showIds) {
			content = section(
					CliTheme.badge("vault") + "  " + CliTheme.title(vault.getName()),
					field(vault.getScope() == VaultScope.TEAM ? "team" : "owner", vault.getScopeId()),
					field("internal id", vault.getId()));
		}
		return content;
	}

	@NotNull
	public static String formatVaults(@NotNull List<IVault> vaults) {
		return formatVaults(vaults, false);
	}

	@NotNull
	public static String formatVaults(@NotNull List<IVault> vaults, boolean showIds) {
		if (vaults.isEmpty()) {
			return CliTheme.muted("No vaults found.");
		}
		StringBuilder builder = new StringBuilder();
		for (IVault vault : vaults) {
			appendSection(builder, formatVault(vault, showIds));
		}
		return builder.toString();
	}

	@NotNull
	public static String formatTeam(@NotNull ITeam team) {
		return section(
				CliTheme.badge("team") + "  " + CliTheme.title(team.name()),
				field("team", team.name()),
				field("owners", joinValues(team.owners())),
				field("members", joinValues(team.memebers())),
				field("default vault", valueOrDash(team.getDefaultVaultId())));
	}

	@NotNull
	public static String formatTeams(@NotNull List<ITeam> teams) {
		if (teams.isEmpty()) {
			return CliTheme.muted("No teams found.");
		}
		StringBuilder builder = new StringBuilder();
		for (ITeam team : teams) {
			appendSection(builder, formatTeam(team));
		}
		return builder.toString();
	}

	@NotNull
	public static String formatEntry(@NotNull EntryView entry) {
		return formatEntry(entry, false);
	}

	@NotNull
	public static String formatEntry(@NotNull EntryView entry, boolean showIds) {
		StringBuilder builder = new StringBuilder(section(
				CliTheme.badge("entry") + "  " + CliTheme.title(entry.getLabel()),
				field("password", entry.getPassword()),
				field("username", valueOrDash(entry.getUsername())),
				field("login", valueOrDash(entry.getLoginName())),
				field("url", valueOrDash(entry.getUrl())),
				field("tags", joinTags(entry.getTags())),
				field("notes", joinNotes(entry.getNotes())),
				field("created", String.valueOf(entry.getCreatedAtEpochMs())),
				field("updated", String.valueOf(entry.getUpdatedAtEpochMs()))));
		if (showIds) {
			builder.setLength(0);
			builder.append(section(
					CliTheme.badge("entry") + "  " + CliTheme.title(entry.getLabel()),
					field("password", entry.getPassword()),
					field("username", valueOrDash(entry.getUsername())),
					field("login", valueOrDash(entry.getLoginName())),
					field("url", valueOrDash(entry.getUrl())),
					field("tags", joinTags(entry.getTags())),
					field("notes", joinNotes(entry.getNotes())),
					field("created", String.valueOf(entry.getCreatedAtEpochMs())),
					field("updated", String.valueOf(entry.getUpdatedAtEpochMs())),
					field("entry id", entry.getId())));
		}
		return builder.toString();
	}

	@NotNull
	public static String formatEntries(@NotNull List<EntryView> entries) {
		return formatEntries(entries, false);
	}

	@NotNull
	public static String formatEntries(@NotNull List<EntryView> entries, boolean showIds) {
		if (entries.isEmpty()) {
			return CliTheme.muted("No entries found.");
		}
		StringBuilder builder = new StringBuilder();
		for (EntryView entry : entries) {
			appendSection(builder, formatEntry(entry, showIds));
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
	private static String section(@NotNull String title, @NotNull String... lines) {
		return CliTheme.panel(title, lines);
	}

	@NotNull
	private static String field(@NotNull String key, @NotNull String value) {
		return CliTheme.key(padRight(key, KEY_WIDTH)) + CliTheme.muted(" : ") + CliTheme.secondary(value);
	}

	@NotNull
	private static String padRight(@NotNull String value, int width) {
		if (value.length() >= width) {
			return value;
		}
		return value + " ".repeat(width - value.length());
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

	@NotNull
	private static String joinRoles(@NotNull List<UserRole> roles) {
		if (roles.isEmpty()) {
			return UserRole.USER.name();
		}
		StringBuilder builder = new StringBuilder();
		for (UserRole role : roles) {
			if (builder.length() > 0) {
				builder.append(", ");
			}
			builder.append(role.name());
		}
		return builder.toString();
	}

	@NotNull
	private static String joinValues(@NotNull List<String> values) {
		if (values.isEmpty()) {
			return "-";
		}
		StringBuilder builder = new StringBuilder();
		for (String value : values) {
			if (builder.length() > 0) {
				builder.append(", ");
			}
			builder.append(value);
		}
		return builder.toString();
	}
}
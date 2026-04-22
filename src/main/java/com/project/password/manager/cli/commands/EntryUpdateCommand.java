package com.project.password.manager.cli.commands;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.project.password.manager.cli.handlers.EntryUpdateCommandHandler;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "update", mixinStandardHelpOptions = true, description = "Updates an existing entry in a vault.")
public class EntryUpdateCommand extends DelegatingCliCommand<EntryUpdateCommand.Request, EntryUpdateCommandHandler> {

	@Option(names = "--vault", description = "Vault name or id. Defaults to the user's default vault.")
	@Nullable
	private String vaultReference;

	@Parameters(index = "0", paramLabel = "entryId", description = "Entry identifier")
	private String entryId;

	@Parameters(index = "1", paramLabel = "label", description = "Entry label")
	private String label;

	@Parameters(index = "2", paramLabel = "password", description = "Entry secret value")
	private String password;

	@Option(names = "--username", description = "Optional username")
	@Nullable
	private String username;

	@Option(names = "--login-name", description = "Optional login name")
	@Nullable
	private String loginName;

	@Option(names = "--url", description = "Optional URL")
	@Nullable
	private String url;

	@Option(names = "--tag", description = "Entry tag value", split = ",")
	@NotNull
	private List<String> tags = new ArrayList<>();

	@Option(names = "--note", description = "Entry note value")
	@NotNull
	private List<String> notes = new ArrayList<>();

	@Override
	@NotNull
	protected Request buildRequest() {
		return new Request(vaultReference, entryId, label, password, username, loginName, url, new ArrayList<>(tags),
				new ArrayList<>(notes));
	}

	public static final class Request {
		@Nullable
		private final String vaultReference;
		@NotNull
		private final String entryId;
		@NotNull
		private final String label;
		@NotNull
		private final String password;
		@Nullable
		private final String username;
		@Nullable
		private final String loginName;
		@Nullable
		private final String url;
		@NotNull
		private final List<String> tags;
		@NotNull
		private final List<String> notes;

		public Request(@Nullable String vaultReference, @NotNull String entryId, @NotNull String label,
				@NotNull String password, @Nullable String username, @Nullable String loginName, @Nullable String url,
				@NotNull List<String> tags, @NotNull List<String> notes) {
			this.vaultReference = vaultReference;
			this.entryId = entryId;
			this.label = label;
			this.password = password;
			this.username = username;
			this.loginName = loginName;
			this.url = url;
			this.tags = tags;
			this.notes = notes;
		}

		@Nullable
		public String getVaultReference() {
			return vaultReference;
		}

		@NotNull
		public String getEntryId() {
			return entryId;
		}

		@NotNull
		public String getLabel() {
			return label;
		}

		@NotNull
		public String getPassword() {
			return password;
		}

		@Nullable
		public String getUsername() {
			return username;
		}

		@Nullable
		public String getLoginName() {
			return loginName;
		}

		@Nullable
		public String getUrl() {
			return url;
		}

		@NotNull
		public List<String> getTags() {
			return tags;
		}

		@NotNull
		public List<String> getNotes() {
			return notes;
		}
	}
}
package com.project.password.manager.cli.commands.entry;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.project.password.manager.cli.commands.DelegatingCliCommand;
import com.project.password.manager.cli.handlers.entry.EntrySearchCommandHandler;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "search", mixinStandardHelpOptions = true, description = "Searches entries in a vault.")
public class EntrySearchCommand extends DelegatingCliCommand<EntrySearchCommand.Request, EntrySearchCommandHandler> {

	@Option(names = "--vault", description = "Vault name or id. Defaults to the user's default vault.")
	@Nullable
	private String vaultReference;

	@Option(names = "--show-ids", description = "Shows internal entry identifiers in the output.")
	private boolean showIds;

	@Parameters(index = "0", paramLabel = "query", description = "Search query")
	private String query;

	@Override
	@NotNull
	protected Request buildRequest() {
		return new Request(vaultReference, query, showIds);
	}

	public static final class Request {
		@Nullable
		private final String vaultReference;
		@NotNull
		private final String query;
		private final boolean showIds;

		public Request(@Nullable String vaultReference, @NotNull String query, boolean showIds) {
			this.vaultReference = vaultReference;
			this.query = query;
			this.showIds = showIds;
		}

		@Nullable
		public String getVaultReference() {
			return vaultReference;
		}

		@NotNull
		public String getQuery() {
			return query;
		}

		public boolean isShowIds() {
			return showIds;
		}
	}
}
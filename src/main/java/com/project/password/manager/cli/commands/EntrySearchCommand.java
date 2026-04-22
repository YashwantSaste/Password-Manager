package com.project.password.manager.cli.commands;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "search", mixinStandardHelpOptions = true, description = "Searches entries in a vault.")
public class EntrySearchCommand extends DelegatingCliCommand<EntrySearchCommand.Request, com.project.password.manager.cli.handlers.EntrySearchCommandHandler> {

	@Option(names = "--vault", description = "Vault name or id. Defaults to the user's default vault.")
	@Nullable
	private String vaultReference;

	@Parameters(index = "0", paramLabel = "query", description = "Search query")
	private String query;

	@Override
	@NotNull
	protected Request buildRequest() {
		return new Request(vaultReference, query);
	}

	public static final class Request {
		@Nullable
		private final String vaultReference;
		@NotNull
		private final String query;

		public Request(@Nullable String vaultReference, @NotNull String query) {
			this.vaultReference = vaultReference;
			this.query = query;
		}

		@Nullable
		public String getVaultReference() {
			return vaultReference;
		}

		@NotNull
		public String getQuery() {
			return query;
		}
	}
}
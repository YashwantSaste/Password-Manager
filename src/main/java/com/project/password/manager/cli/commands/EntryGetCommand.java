package com.project.password.manager.cli.commands;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "get", mixinStandardHelpOptions = true, description = "Gets one entry by id from a vault.")
public class EntryGetCommand extends DelegatingCliCommand<EntryGetCommand.Request, com.project.password.manager.cli.handlers.EntryGetCommandHandler> {

	@Option(names = "--vault", description = "Vault name or id. Defaults to the user's default vault.")
	@Nullable
	private String vaultReference;

	@Parameters(index = "0", paramLabel = "entryId", description = "Entry identifier")
	private String entryId;

	@Override
	@NotNull
	protected Request buildRequest() {
		return new Request(vaultReference, entryId);
	}

	public static final class Request {
		@Nullable
		private final String vaultReference;
		@NotNull
		private final String entryId;

		public Request(@Nullable String vaultReference, @NotNull String entryId) {
			this.vaultReference = vaultReference;
			this.entryId = entryId;
		}

		@Nullable
		public String getVaultReference() {
			return vaultReference;
		}

		@NotNull
		public String getEntryId() {
			return entryId;
		}
	}
}
package com.project.password.manager.cli.commands;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.project.password.manager.cli.handlers.EntryGetCommandHandler;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "get", mixinStandardHelpOptions = true, description = "Gets entries by id or exact label from one vault or across all vaults.")
public class EntryGetCommand extends DelegatingCliCommand<EntryGetCommand.Request, EntryGetCommandHandler> {

	@Option(names = "--vault", description = "Vault name or id. Defaults to the user's default vault.")
	@Nullable
	private String vaultReference;

	@Option(names = "--show-ids", description = "Shows internal entry identifiers in the output.")
	private boolean showIds;

	@Parameters(index = "0", paramLabel = "entryReference", description = "Entry id or exact label")
	private String entryReference;

	@Override
	@NotNull
	protected Request buildRequest() {
		return new Request(vaultReference, entryReference, showIds);
	}

	public static final class Request {
		@Nullable
		private final String vaultReference;
		@NotNull
		private final String entryReference;
		private final boolean showIds;

		public Request(@Nullable String vaultReference, @NotNull String entryReference, boolean showIds) {
			this.vaultReference = vaultReference;
			this.entryReference = entryReference;
			this.showIds = showIds;
		}

		@Nullable
		public String getVaultReference() {
			return vaultReference;
		}

		@NotNull
		public String getEntryReference() {
			return entryReference;
		}

		public boolean isShowIds() {
			return showIds;
		}
	}
}
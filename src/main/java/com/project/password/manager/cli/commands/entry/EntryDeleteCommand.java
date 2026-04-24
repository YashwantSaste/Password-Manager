package com.project.password.manager.cli.commands.entry;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.project.password.manager.cli.commands.DelegatingCliCommand;
import com.project.password.manager.cli.handlers.entry.EntryDeleteCommandHandler;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "delete", mixinStandardHelpOptions = true, description = "Deletes an entry from a vault.")
public class EntryDeleteCommand extends DelegatingCliCommand<EntryDeleteCommand.Request, EntryDeleteCommandHandler> {

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
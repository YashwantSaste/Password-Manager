package com.project.password.manager.cli.commands.entry;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.project.password.manager.cli.commands.DelegatingCliCommand;
import com.project.password.manager.cli.handlers.entry.EntryListCommandHandler;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "list", mixinStandardHelpOptions = true, description = "Lists all entries in a vault.")
public class EntryListCommand extends DelegatingCliCommand<EntryListCommand.Request, EntryListCommandHandler> {

	@Option(names = "--vault", description = "Vault name or id. Defaults to the user's default vault.")
	@Nullable
	private String vaultReference;

	@Option(names = "--show-ids", description = "Shows internal entry identifiers in the output.")
	private boolean showIds;

	@Override
	@NotNull
	protected Request buildRequest() {
		return new Request(vaultReference, showIds);
	}

	public static final class Request {
		@Nullable
		private final String vaultReference;
		private final boolean showIds;

		public Request(@Nullable String vaultReference, boolean showIds) {
			this.vaultReference = vaultReference;
			this.showIds = showIds;
		}

		@Nullable
		public String getVaultReference() {
			return vaultReference;
		}

		public boolean isShowIds() {
			return showIds;
		}
	}
}
package com.project.password.manager.cli.commands;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.project.password.manager.cli.handlers.EntryListCommandHandler;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "list", mixinStandardHelpOptions = true, description = "Lists all entries in a vault.")
public class EntryListCommand extends DelegatingCliCommand<EntryListCommand.Request, EntryListCommandHandler> {

	@Option(names = "--vault", description = "Vault name or id. Defaults to the user's default vault.")
	@Nullable
	private String vaultReference;

	@Override
	@NotNull
	protected Request buildRequest() {
		return new Request(vaultReference);
	}

	public static final class Request {
		@Nullable
		private final String vaultReference;

		public Request(@Nullable String vaultReference) {
			this.vaultReference = vaultReference;
		}

		@Nullable
		public String getVaultReference() {
			return vaultReference;
		}
	}
}
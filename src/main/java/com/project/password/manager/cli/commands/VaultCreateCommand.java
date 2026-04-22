package com.project.password.manager.cli.commands;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.cli.handlers.VaultCreateCommandHandler;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "create", mixinStandardHelpOptions = true, description = "Creates a new vault for the authenticated user.")
public class VaultCreateCommand extends DelegatingCliCommand<VaultCreateCommand.Request, VaultCreateCommandHandler> {

	@Parameters(index = "0", paramLabel = "vaultName", description = "Vault display name")
	private String vaultName;

	@Override
	@NotNull
	protected Request buildRequest() {
		return new Request(vaultName);
	}

	public static final class Request {
		@NotNull
		private final String vaultName;

		public Request(@NotNull String vaultName) {
			this.vaultName = vaultName;
		}

		@NotNull
		public String getVaultName() {
			return vaultName;
		}
	}
}
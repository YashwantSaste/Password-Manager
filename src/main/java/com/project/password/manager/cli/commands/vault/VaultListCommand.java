package com.project.password.manager.cli.commands.vault;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.cli.commands.DelegatingCliCommand;
import com.project.password.manager.cli.handlers.vault.VaultListCommandHandler;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "list", mixinStandardHelpOptions = true, description = "Lists vaults for the authenticated user.")
public class VaultListCommand extends DelegatingCliCommand<VaultListCommand.Request, VaultListCommandHandler> {

	@Option(names = "--show-ids", description = "Shows internal vault identifiers in the output.")
	private boolean showIds;

	@Override
	@NotNull
	protected Request buildRequest() {
		return new Request(showIds);
	}

	public static final class Request {
		private final boolean showIds;

		public Request(boolean showIds) {
			this.showIds = showIds;
		}

		public boolean isShowIds() {
			return showIds;
		}
	}
}
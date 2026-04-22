package com.project.password.manager.cli.commands;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.cli.handlers.VaultDefaultCommandHandler;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "default", mixinStandardHelpOptions = true, description = "Shows the default vault for the authenticated user.")
public class VaultDefaultCommand extends DelegatingCliCommand<VaultDefaultCommand.Request, VaultDefaultCommandHandler> {

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
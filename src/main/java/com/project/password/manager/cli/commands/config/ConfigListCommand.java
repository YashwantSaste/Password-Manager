package com.project.password.manager.cli.commands.config;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.cli.commands.DelegatingCliCommand;
import com.project.password.manager.cli.handlers.config.ConfigListCommandHandler;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "list", mixinStandardHelpOptions = true, description = "Lists supported configuration properties and current values.")
public class ConfigListCommand extends DelegatingCliCommand<ConfigListCommand.Request, ConfigListCommandHandler> {

	@Option(names = "--show-sensitive", description = "Shows sensitive property values instead of masking them.")
	private boolean showSensitive;

	@Override
	@NotNull
	protected Request buildRequest() {
		return new Request(showSensitive);
	}

	public static final class Request {
		private final boolean showSensitive;

		public Request(boolean showSensitive) {
			this.showSensitive = showSensitive;
		}

		public boolean isShowSensitive() {
			return showSensitive;
		}
	}
}
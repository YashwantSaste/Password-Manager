package com.project.password.manager.cli.commands;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.cli.handlers.ConfigGetCommandHandler;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "get", mixinStandardHelpOptions = true, description = "Gets the current value of one configuration property.")
public class ConfigGetCommand extends DelegatingCliCommand<ConfigGetCommand.Request, ConfigGetCommandHandler> {

	@Parameters(index = "0", description = "Configuration property key.")
	private String key;

	@Option(names = "--show-sensitive", description = "Shows sensitive property values instead of masking them.")
	private boolean showSensitive;

	@Override
	@NotNull
	protected Request buildRequest() {
		return new Request(key, showSensitive);
	}

	public static final class Request {
		private final String key;
		private final boolean showSensitive;

		public Request(@NotNull String key, boolean showSensitive) {
			this.key = key;
			this.showSensitive = showSensitive;
		}

		@NotNull
		public String getKey() {
			return key;
		}

		public boolean isShowSensitive() {
			return showSensitive;
		}
	}
}
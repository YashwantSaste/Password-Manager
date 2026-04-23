package com.project.password.manager.cli.commands;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.cli.handlers.ConfigSetCommandHandler;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "set", mixinStandardHelpOptions = true, description = "Overrides one configuration property in the workspace properties file.")
public class ConfigSetCommand extends DelegatingCliCommand<ConfigSetCommand.Request, ConfigSetCommandHandler> {

	@Parameters(index = "0", description = "Configuration property key.")
	private String key;

	@Parameters(index = "1", description = "New property value.")
	private String value;

	@Override
	@NotNull
	protected Request buildRequest() {
		return new Request(key, value);
	}

	public static final class Request {
		private final String key;
		private final String value;

		public Request(@NotNull String key, @NotNull String value) {
			this.key = key;
			this.value = value;
		}

		@NotNull
		public String getKey() {
			return key;
		}

		@NotNull
		public String getValue() {
			return value;
		}
	}
}
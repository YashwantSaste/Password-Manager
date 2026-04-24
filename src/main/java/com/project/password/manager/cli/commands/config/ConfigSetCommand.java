package com.project.password.manager.cli.commands.config;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.project.password.manager.cli.commands.DelegatingCliCommand;
import com.project.password.manager.cli.handlers.config.ConfigSetCommandHandler;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "set", mixinStandardHelpOptions = true, description = "Overrides one configuration property in the workspace properties file.")
public class ConfigSetCommand extends DelegatingCliCommand<ConfigSetCommand.Request, ConfigSetCommandHandler> {

	@Parameters(index = "0", description = "Configuration property key.")
	private String key;

	@Parameters(index = "1", description = "New property value.")
	private String value;

	@Parameters(index = "2", description = "Comment for the configuration update.")
	private String comment;

	@Override
	@NotNull
	protected Request buildRequest() {
		return new Request(key, value, comment);
	}

	public static final class Request {
		private final String key;
		private final String value;
		private String comment = "Updated CLI configuration property. ";

		public Request(@NotNull String key, @NotNull String value, @Nullable String comment) {
			this.key = key;
			this.value = value;
			if (comment != null) {
				this.comment = comment;
			}
		}

		@NotNull
		public String getKey() {
			return key;
		}

		@NotNull
		public String getValue() {
			return value;
		}

		@Nullable
		public String getComment() {
			return comment;
		}
	}
}
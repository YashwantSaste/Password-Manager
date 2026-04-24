package com.project.password.manager.configuration.application;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.configuration.ICLIConfiguration;

public class CLIConfiguration implements ICLIConfiguration {

	@NotNull
	private final PropertiesReader reader;

	public CLIConfiguration(@NotNull PropertiesReader reader) {
		this.reader = reader;
	}

	@Override
	public boolean isEnabled() {
		return reader.readPropertyAsBoolean(ApplicationProperties.PROPERTY_APP_CLI_ENABLED, true);
	}

	@Override
	@NotNull
	public String displayPrompt() {
		return reader.readPropertyAsString(ApplicationProperties.PROPERTY_APP_CLI_DISPLAY_PROMPT);
	}

	@Override
	@NotNull
	public String theme() {
		return reader.readPropertyAsString(ApplicationProperties.PROPERTY_APP_CLI_THEME);
	}

}

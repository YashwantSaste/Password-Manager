package com.project.password.manager.cli.handlers;

import java.io.IOException;
import java.util.Properties;

import org.jetbrains.annotations.NotNull;

import com.google.inject.Inject;
import com.project.password.manager.cli.commands.ConfigSetCommand;
import com.project.password.manager.cli.runtime.CliOutput;
import com.project.password.manager.cli.runtime.CliTheme;
import com.project.password.manager.configuration.application.ApplicationProperties;
import com.project.password.manager.configuration.application.PropertiesReader;
import com.project.password.manager.middleware.RequireAuthorization;

public class ConfigSetCommandHandler implements CommandHandler<ConfigSetCommand.Request> {

	@NotNull
	private final CliOutput output;

	@Inject
	public ConfigSetCommandHandler(@NotNull CliOutput output) {
		this.output = output;
	}

	@Override
	@RequireAuthorization
	public void handle(@NotNull ConfigSetCommand.Request request) {
		String key = request.getKey();
		String value = request.getValue();
		if (!ApplicationProperties.isSupportedKey(key)) {
			throw new IllegalArgumentException("Unsupported configuration key: " + key);
		}

		if (ApplicationProperties.PROPERTY_APP_CLI_THEME.equals(key) && !CliTheme.isSupportedTheme(value)) {
			throw new IllegalArgumentException("Unsupported theme: " + value);
		}

		try {
			Properties properties = PropertiesReader.loadProperties();
			properties.setProperty(key, value);
			PropertiesReader.storeProperties(properties, "Updated CLI configuration property");
			PropertiesReader.refreshIfInitialized();
			if (ApplicationProperties.PROPERTY_APP_CLI_THEME.equals(key)) {
				CliTheme.initialize();
			}
		} catch (IOException ioException) {
			throw new RuntimeException("Failed to persist configuration property", ioException);
		}

		output.info(CliTheme.successPanel("Configuration Updated",
				CliTheme.key("key") + CliTheme.muted(" : ") + CliTheme.secondary(key),
				CliTheme.key("value") + CliTheme.muted(" : ")
				+ CliTheme.secondary(ConfigPropertyFormatter.displayValue(key, value, false))));
	}
}
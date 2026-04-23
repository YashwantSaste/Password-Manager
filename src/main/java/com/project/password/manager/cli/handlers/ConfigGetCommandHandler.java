package com.project.password.manager.cli.handlers;

import java.io.IOException;
import java.util.Properties;

import org.jetbrains.annotations.NotNull;

import com.google.inject.Inject;
import com.project.password.manager.cli.commands.ConfigGetCommand;
import com.project.password.manager.cli.runtime.CliOutput;
import com.project.password.manager.cli.runtime.CliTheme;
import com.project.password.manager.configuration.application.ApplicationProperties;
import com.project.password.manager.configuration.application.PropertiesReader;
import com.project.password.manager.middleware.RequireAuthorization;

public class ConfigGetCommandHandler implements CommandHandler<ConfigGetCommand.Request> {

	@NotNull
	private final CliOutput output;

	@Inject
	public ConfigGetCommandHandler(@NotNull CliOutput output) {
		this.output = output;
	}

	@Override
	@RequireAuthorization
	public void handle(@NotNull ConfigGetCommand.Request request) {
		String key = request.getKey();
		if (!ApplicationProperties.isSupportedKey(key)) {
			throw new IllegalArgumentException("Unsupported configuration key: " + key);
		}

		Properties properties;
		try {
			properties = PropertiesReader.loadProperties();
		} catch (IOException ioException) {
			throw new RuntimeException("Failed to read configuration properties", ioException);
		}

		output.info(CliTheme.infoPanel("Configuration Value",
				CliTheme.key("key") + CliTheme.muted(" : ") + CliTheme.secondary(key),
				CliTheme.key("value") + CliTheme.muted(" : ") + CliTheme.secondary(
						ConfigPropertyFormatter.displayValue(key, properties.getProperty(key), request.isShowSensitive()))));
	}
}
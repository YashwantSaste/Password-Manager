package com.project.password.manager.cli.handlers;

import java.io.IOException;
import java.util.Properties;

import org.jetbrains.annotations.NotNull;

import com.google.inject.Inject;
import com.project.password.manager.cli.commands.ThemeSetCommand;
import com.project.password.manager.cli.runtime.CliOutput;
import com.project.password.manager.cli.runtime.CliTheme;
import com.project.password.manager.configuration.application.ApplicationProperties;
import com.project.password.manager.configuration.application.PropertiesReader;

public class ThemeSetCommandHandler implements CommandHandler<ThemeSetCommand.Request> {

	@NotNull
	private final CliOutput output;

	@Inject
	public ThemeSetCommandHandler(@NotNull CliOutput output) {
		this.output = output;
	}

	@Override
	public void handle(@NotNull ThemeSetCommand.Request request) {
		String themeName = request.getThemeName();
		if (!CliTheme.isSupportedTheme(themeName)) {
			throw new IllegalArgumentException("Unsupported theme: " + themeName);
		}

		try {
			Properties properties = PropertiesReader.loadProperties();
			properties.setProperty(ApplicationProperties.PROPERTY_APP_CLI_THEME, themeName);
			PropertiesReader.storeProperties(properties, "Updated CLI theme");
			PropertiesReader.refreshIfInitialized();
			CliTheme.initialize();
		} catch (IOException ioException) {
			throw new RuntimeException("Failed to persist theme setting", ioException);
		}

		output.info(CliTheme.successPanel("Theme Updated",
				CliTheme.key("active") + CliTheme.muted(" : ") + CliTheme.secondary(CliTheme.getActiveThemeName()),
				CliTheme.key("preview") + CliTheme.muted(" : ") + CliTheme.accent("theme preview " + themeName)));
	}
}
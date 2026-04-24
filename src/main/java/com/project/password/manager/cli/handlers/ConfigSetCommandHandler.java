package com.project.password.manager.cli.handlers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import org.jetbrains.annotations.NotNull;

import com.google.inject.Inject;
import com.project.password.manager.cli.commands.ConfigSetCommand;
import com.project.password.manager.cli.runtime.CliOutput;
import com.project.password.manager.cli.runtime.CliSession;
import com.project.password.manager.cli.runtime.CliTheme;
import com.project.password.manager.configuration.application.ApplicationProperties;
import com.project.password.manager.configuration.application.PropertiesReader;
import com.project.password.manager.middleware.RequireAuthorization;
import com.project.password.manager.model.UserRole;
import com.project.password.manager.service.UserService;

public class ConfigSetCommandHandler extends AbstractAuthorizedCommandHandler<ConfigSetCommand.Request> {

	@Inject
	public ConfigSetCommandHandler(@NotNull CliSession session, @NotNull UserService userService,
			@NotNull CliOutput output) {
		super(session, userService, output);
	}

	@Override
	@RequireAuthorization(roles = { UserRole.ADMIN })
	public void handle(@NotNull ConfigSetCommand.Request request) {
		String key = request.getKey();
		String value = request.getValue();
		String comment = request.getComment();
		if (!ApplicationProperties.isSupportedKey(key)) {
			throw new IllegalArgumentException("Unsupported configuration key: " + key);
		}

		if (ApplicationProperties.PROPERTY_APP_CLI_THEME.equals(key) && !CliTheme.isSupportedTheme(value)) {
			throw new IllegalArgumentException("Unsupported theme: " + value);
		}

		try {
			Properties properties = PropertiesReader.loadProperties();
			properties.setProperty(key, value);
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
			String formattedDayDateTime = now.format(formatter);
			String updatedBy = currentUser().getName();
			PropertiesReader.storeProperties(properties,
					comment + " Updated by user: [" + updatedBy + "] at " + formattedDayDateTime);
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
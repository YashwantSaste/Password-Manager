package com.project.password.manager.cli.handlers.config;

import java.io.IOException;
import java.util.Properties;

import org.jetbrains.annotations.NotNull;

import com.google.inject.Inject;
import com.project.password.manager.cli.commands.config.ConfigListCommand;
import com.project.password.manager.cli.handlers.CommandHandler;
import com.project.password.manager.cli.runtime.CliOutput;
import com.project.password.manager.cli.runtime.CliTheme;
import com.project.password.manager.configuration.application.ApplicationProperties;
import com.project.password.manager.configuration.application.PropertiesReader;
import com.project.password.manager.middleware.RequireAuthorization;
import com.project.password.manager.model.UserRole;

public class ConfigListCommandHandler implements CommandHandler<ConfigListCommand.Request> {

	@NotNull
	private final CliOutput output;

	@Inject
	public ConfigListCommandHandler(@NotNull CliOutput output) {
		this.output = output;
	}

	@Override
	@RequireAuthorization(roles = { UserRole.ADMIN })
	public void handle(@NotNull ConfigListCommand.Request request) {
		Properties properties;
		try {
			properties = PropertiesReader.loadProperties();
		} catch (IOException ioException) {
			throw new RuntimeException("Failed to read configuration properties", ioException);
		}

		StringBuilder builder = new StringBuilder();
		builder.append(CliTheme.infoPanel("Configuration Properties",
				CliTheme.key("file") + CliTheme.muted(" : ")
				+ CliTheme.secondary(PropertiesReader.resolvePropertiesFile().getAbsolutePath()),
				CliTheme.key("count") + CliTheme.muted(" : ")
				+ CliTheme.secondary(String.valueOf(ApplicationProperties.supportedKeys().size()))));
		builder.append(System.lineSeparator()).append(System.lineSeparator());
		builder.append(CliTheme.title("Known Keys")).append(System.lineSeparator()).append(CliTheme.line());
		for (String key : ApplicationProperties.supportedKeys()) {
			builder.append(System.lineSeparator())
			.append(CliTheme.key(key))
			.append(CliTheme.muted(" = "))
			.append(CliTheme.secondary(ConfigPropertyFormatter.displayValue(key, properties.getProperty(key),
					request.isShowSensitive())));
		}
		output.info(builder.toString());
	}
}
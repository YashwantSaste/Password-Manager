package com.project.password.manager.cli.handlers.config;

import java.util.Map;

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
import com.project.password.manager.service.ConfigService;

public class ConfigListCommandHandler implements CommandHandler<ConfigListCommand.Request> {

	@NotNull
	private final CliOutput output;
	@NotNull
	private final ConfigService configService;

	@Inject
	public ConfigListCommandHandler(@NotNull CliOutput output, @NotNull ConfigService configService) {
		this.output = output;
		this.configService = configService;
	}

	@Override
	@RequireAuthorization(roles = { UserRole.ADMIN })
	public void handle(@NotNull ConfigListCommand.Request request) {
		Map<String, String> properties = configService.getProperties(request.isShowSensitive());

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
			.append(CliTheme.secondary(properties.get(key)));
		}
		output.info(builder.toString());
	}
}
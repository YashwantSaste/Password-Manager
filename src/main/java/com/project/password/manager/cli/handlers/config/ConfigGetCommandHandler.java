package com.project.password.manager.cli.handlers.config;

import org.jetbrains.annotations.NotNull;

import com.google.inject.Inject;
import com.project.password.manager.cli.commands.config.ConfigGetCommand;
import com.project.password.manager.cli.handlers.CommandHandler;
import com.project.password.manager.cli.runtime.CliOutput;
import com.project.password.manager.cli.runtime.CliTheme;
import com.project.password.manager.middleware.RequireAuthorization;
import com.project.password.manager.model.UserRole;
import com.project.password.manager.service.ConfigService;

public class ConfigGetCommandHandler implements CommandHandler<ConfigGetCommand.Request> {

	@NotNull
	private final CliOutput output;
	@NotNull
	private final ConfigService configService;

	@Inject
	public ConfigGetCommandHandler(@NotNull CliOutput output, @NotNull ConfigService configService) {
		this.output = output;
		this.configService = configService;
	}

	@Override
	@RequireAuthorization(roles = { UserRole.ADMIN })
	public void handle(@NotNull ConfigGetCommand.Request request) {
		String key = request.getKey();

		output.info(CliTheme.infoPanel("Configuration Value",
				CliTheme.key("key") + CliTheme.muted(" : ") + CliTheme.secondary(key),
				CliTheme.key("value") + CliTheme.muted(" : ") + CliTheme.secondary(
						configService.getProperty(key, request.isShowSensitive()))));
	}
}
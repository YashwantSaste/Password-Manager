package com.project.password.manager.cli.handlers.config;

import org.jetbrains.annotations.NotNull;

import com.google.inject.Inject;
import com.project.password.manager.cli.commands.config.ConfigSetCommand;
import com.project.password.manager.cli.handlers.AbstractAuthorizedCommandHandler;
import com.project.password.manager.cli.runtime.CliOutput;
import com.project.password.manager.cli.runtime.CliSession;
import com.project.password.manager.cli.runtime.CliTheme;
import com.project.password.manager.middleware.RequireAuthorization;
import com.project.password.manager.model.UserRole;
import com.project.password.manager.service.ConfigService;
import com.project.password.manager.service.UserService;

public class ConfigSetCommandHandler extends AbstractAuthorizedCommandHandler<ConfigSetCommand.Request> {
	@NotNull
	private final ConfigService configService;

	@Inject
	public ConfigSetCommandHandler(@NotNull CliSession session, @NotNull UserService userService,
			@NotNull CliOutput output, @NotNull ConfigService configService) {
		super(session, userService, output);
		this.configService = configService;
	}

	@Override
	@RequireAuthorization(roles = { UserRole.ADMIN })
	public void handle(@NotNull ConfigSetCommand.Request request) {
		String key = request.getKey();
		String value = request.getValue();
		configService.updateProperty(key, value, request.getComment(), currentUser().getName());

		output.info(CliTheme.successPanel("Configuration Updated",
				CliTheme.key("key") + CliTheme.muted(" : ") + CliTheme.secondary(key),
				CliTheme.key("value") + CliTheme.muted(" : ")
				+ CliTheme.secondary(configService.getProperty(key, false))));
	}
}
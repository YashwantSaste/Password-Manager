package com.project.password.manager.cli.handlers.auth;

import org.jetbrains.annotations.NotNull;

import com.google.inject.Inject;
import com.project.password.manager.cli.commands.auth.LogoutCommand;
import com.project.password.manager.cli.handlers.AbstractAuthorizedCommandHandler;
import com.project.password.manager.cli.runtime.CliOutput;
import com.project.password.manager.cli.runtime.CliSession;
import com.project.password.manager.middleware.RequireAuthorization;
import com.project.password.manager.service.TokenService;
import com.project.password.manager.service.UserService;

public class LogoutCommandHandler extends AbstractAuthorizedCommandHandler<LogoutCommand.Request> {

	@NotNull
	private final TokenService tokenService;

	@Inject
	public LogoutCommandHandler(@NotNull CliSession session, @NotNull UserService userService,
			@NotNull TokenService tokenService, @NotNull CliOutput output) {
		super(session, userService, output);
		this.tokenService = tokenService;
	}

	@Override
	@RequireAuthorization
	public void handle(@NotNull LogoutCommand.Request request) {
		tokenService.revokeToken(currentUser());
		clearSession();
		output.info("Logged out successfully.");
	}
}
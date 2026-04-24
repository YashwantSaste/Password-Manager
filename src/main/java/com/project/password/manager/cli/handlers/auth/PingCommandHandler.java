package com.project.password.manager.cli.handlers.auth;

import org.jetbrains.annotations.NotNull;

import com.google.inject.Inject;
import com.project.password.manager.cli.commands.auth.PingCommand;
import com.project.password.manager.cli.handlers.AbstractAuthorizedCommandHandler;
import com.project.password.manager.cli.runtime.CliOutput;
import com.project.password.manager.cli.runtime.CliSession;
import com.project.password.manager.middleware.RequireAuthorization;
import com.project.password.manager.service.UserService;

public class PingCommandHandler extends AbstractAuthorizedCommandHandler<PingCommand.Request> {

	@Inject
	public PingCommandHandler(@NotNull CliSession session, @NotNull UserService userService,
			@NotNull CliOutput output) {
		super(session, userService, output);
	}

	@Override
	@RequireAuthorization
	public void handle(@NotNull PingCommand.Request request) {
		output.info(com.project.password.manager.cli.runtime.CliTheme.successPanel("Session Healthy",
				com.project.password.manager.cli.runtime.CliTheme.key("user")
						+ com.project.password.manager.cli.runtime.CliTheme.muted(" : ")
						+ com.project.password.manager.cli.runtime.CliTheme.secondary(currentUserId()),
				com.project.password.manager.cli.runtime.CliTheme.key("status")
						+ com.project.password.manager.cli.runtime.CliTheme.muted(" : ")
						+ com.project.password.manager.cli.runtime.CliTheme.success("pong")));
	}
}
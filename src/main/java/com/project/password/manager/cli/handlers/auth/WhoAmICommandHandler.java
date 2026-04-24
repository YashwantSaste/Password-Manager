package com.project.password.manager.cli.handlers.auth;

import org.jetbrains.annotations.NotNull;

import com.google.inject.Inject;
import com.project.password.manager.cli.commands.auth.WhoAmICommand;
import com.project.password.manager.cli.handlers.AbstractAuthorizedCommandHandler;
import com.project.password.manager.cli.handlers.CliViewPrinter;
import com.project.password.manager.cli.runtime.CliOutput;
import com.project.password.manager.cli.runtime.CliSession;
import com.project.password.manager.middleware.RequireAuthorization;
import com.project.password.manager.model.IUser;
import com.project.password.manager.service.UserService;

public class WhoAmICommandHandler extends AbstractAuthorizedCommandHandler<WhoAmICommand.Request> {

	@Inject
	public WhoAmICommandHandler(@NotNull CliSession session, @NotNull UserService userService,
			@NotNull CliOutput output) {
		super(session, userService, output);
	}

	@Override
	@RequireAuthorization
	public void handle(@NotNull WhoAmICommand.Request request) {
		IUser user = currentUser();
		output.info(CliViewPrinter.formatUser(user));
	}
}
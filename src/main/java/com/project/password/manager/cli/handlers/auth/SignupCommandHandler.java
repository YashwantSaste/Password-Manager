package com.project.password.manager.cli.handlers.auth;

import org.jetbrains.annotations.NotNull;

import com.google.inject.Inject;
import com.project.password.manager.cli.commands.auth.SignupCommand;
import com.project.password.manager.cli.handlers.CommandHandler;
import com.project.password.manager.cli.runtime.CliOutput;
import com.project.password.manager.cli.runtime.CliSession;
import com.project.password.manager.exceptions.UnauthorizedSessionException;
import com.project.password.manager.model.IUser;
import com.project.password.manager.service.AuthService;
import com.project.password.manager.service.TokenService;
import com.project.password.manager.service.UserService;

public class SignupCommandHandler implements CommandHandler<SignupCommand.Request> {

	@NotNull
	private final AuthService authService;
	@NotNull
	private final UserService userService;
	@NotNull
	private final TokenService tokenService;
	@NotNull
	private final CliSession session;
	@NotNull
	private final CliOutput output;

	@Inject
	public SignupCommandHandler(@NotNull AuthService authService, @NotNull UserService userService,
			@NotNull TokenService tokenService, @NotNull CliSession session, @NotNull CliOutput output) {
		this.authService = authService;
		this.userService = userService;
		this.tokenService = tokenService;
		this.session = session;
		this.output = output;
	}

	@Override
	public void handle(@NotNull SignupCommand.Request request) {
		authService.signup(request.getUsername(), request.getPassword());
		IUser user = userService.getUser(request.getUsername());
		String token = user == null ? null : tokenService.getToken(user);
		if (user == null || token == null || token.isBlank()) {
			throw new UnauthorizedSessionException("Unable to open a CLI session after signup.");
		}
		session.open(user.getId(), token);
		output.info("Signed up and logged in as " + user.getId());
	}
}
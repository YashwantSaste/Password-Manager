package com.project.password.manager.cli.handlers.auth;

import org.jetbrains.annotations.NotNull;

import com.google.inject.Inject;
import com.project.password.manager.cli.commands.auth.LoginCommand;
import com.project.password.manager.cli.handlers.CommandHandler;
import com.project.password.manager.cli.runtime.CliOutput;
import com.project.password.manager.cli.runtime.CliSession;
import com.project.password.manager.exceptions.UnauthorizedSessionException;
import com.project.password.manager.model.IUser;
import com.project.password.manager.service.AuthService;
import com.project.password.manager.service.TokenService;
import com.project.password.manager.service.UserService;

public class LoginCommandHandler implements CommandHandler<LoginCommand.Request> {

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
	public LoginCommandHandler(@NotNull AuthService authService, @NotNull UserService userService,
			@NotNull TokenService tokenService, @NotNull CliSession session, @NotNull CliOutput output) {
		this.authService = authService;
		this.userService = userService;
		this.tokenService = tokenService;
		this.session = session;
		this.output = output;
	}

	@Override
	public void handle(@NotNull LoginCommand.Request request) {
		authService.login(request.getUsername(), request.getPassword());
		IUser user = resolveAuthenticatedUser(request.getUsername(), "login");
		String token = resolveCliToken(user, "login");
		session.open(user.getId(), token);
		output.info("Logged in as " + user.getId());
	}

	@NotNull
	private IUser resolveAuthenticatedUser(@NotNull String username, @NotNull String action) {
		try {
			return userService.requireUser(username);
		} catch (IllegalArgumentException exception) {
			throw new UnauthorizedSessionException("Unable to open a CLI session after " + action + ".", exception);
		}
	}

	@NotNull
	private String resolveCliToken(@NotNull IUser user, @NotNull String action) {
		try {
			return tokenService.requireToken(user);
		} catch (IllegalStateException exception) {
			throw new UnauthorizedSessionException("Unable to open a CLI session after " + action + ".", exception);
		}
	}
}
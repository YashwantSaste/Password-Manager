package com.project.password.manager.cli.handlers;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.cli.runtime.CliOutput;
import com.project.password.manager.cli.runtime.CliSession;
import com.project.password.manager.exceptions.UnauthorizedSessionException;
import com.project.password.manager.model.IUser;
import com.project.password.manager.service.UserService;

public abstract class AbstractAuthorizedCommandHandler<TRequest> implements CommandHandler<TRequest> {

	@NotNull
	private final CliSession session;
	@NotNull
	private final UserService userService;
	@NotNull
	protected final CliOutput output;

	protected AbstractAuthorizedCommandHandler(@NotNull CliSession session, @NotNull UserService userService,
			@NotNull CliOutput output) {
		this.session = session;
		this.userService = userService;
		this.output = output;
	}

	@NotNull
	protected final String currentUserId() {
		return session.requireUserId();
	}

	@NotNull
	protected final IUser currentUser() {
		IUser user = userService.getUser(currentUserId());
		if (user == null) {
			throw new UnauthorizedSessionException("Authenticated user no longer exists.");
		}
		return user;
	}

	protected final void clearSession() {
		session.clear();
	}
}
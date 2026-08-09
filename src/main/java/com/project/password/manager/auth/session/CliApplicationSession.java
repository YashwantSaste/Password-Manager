package com.project.password.manager.auth.session;

import com.google.inject.Inject;
import com.project.password.manager.cli.runtime.CliSession;
import com.project.password.manager.model.IUser;
import com.project.password.manager.model.database.file.storage.Token;
import com.project.password.manager.validation.utlis.UserValidationUtils;

import jakarta.validation.constraints.NotNull;

public class CliApplicationSession implements ISession {

	private final CliSession cliSession;

	private final String userId;

	@Inject
	public CliApplicationSession(@NotNull CliSession cliSession) {
		this.cliSession = cliSession;
		this.userId = cliSession.requireUserId();
	}

	@Override
	@NotNull
	public ApplicationSession getCurrentSession() {
		return new ApplicationSession(userId, new Token(cliSession.requireUserId(), cliSession.requireToken()));
	}

	@Override
	@NotNull
	public IUser getCurrentLoggedInUser() {
		return UserValidationUtils.requireUser(userId);
	}
}

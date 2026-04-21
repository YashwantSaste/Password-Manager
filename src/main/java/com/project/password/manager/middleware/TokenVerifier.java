package com.project.password.manager.middleware;

import org.jetbrains.annotations.NotNull;

import com.google.inject.Inject;
import com.project.password.manager.configuration.application.Configuration;
import com.project.password.manager.exceptions.UnauthorizedSessionException;
import com.project.password.manager.model.IToken;
import com.project.password.manager.model.IUser;
import com.project.password.manager.service.TokenService;

public class TokenVerifier {

	@NotNull
	private TokenService tokenService;

	@Inject
	public TokenVerifier() {
		this.tokenService = new TokenService(Configuration.getInstance().jwtConfiguration());
	}

	public void validateTokenForUser(@NotNull IUser user, @NotNull IToken token) {
		String rawToken = token.getToken();
		if (tokenService.verify(rawToken, user) == null) {
			throw new UnauthorizedSessionException("Session Expired.");
		}
	}
}

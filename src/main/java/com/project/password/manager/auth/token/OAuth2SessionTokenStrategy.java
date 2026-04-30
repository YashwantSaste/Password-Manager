package com.project.password.manager.auth.token;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.configuration.AuthenticationType;
import com.project.password.manager.model.IToken;
import com.project.password.manager.model.IUser;
import com.project.password.manager.util.ValidationUtils;

public final class OAuth2SessionTokenStrategy implements SessionTokenStrategy {

	@Override
	@NotNull
	public AuthenticationType getAuthenticationType() {
		return AuthenticationType.OAUTH2;
	}

	@Override
	@NotNull
	public String issueToken(@NotNull IUser user, @NotNull SessionTokenRequest request) {
		return ValidationUtils.requireText(request.getSourceToken(),
				"OAuth2 session token issuance requires the provider access token.");
	}

	@Override
	public boolean canReuse(@NotNull IToken persistedToken, @NotNull IUser user) {
		return false;
	}

	@Override
	public boolean isValid(@NotNull IToken persistedToken, @NotNull IUser user) {
		return ValidationUtils.hasText(persistedToken.getToken());
	}
}
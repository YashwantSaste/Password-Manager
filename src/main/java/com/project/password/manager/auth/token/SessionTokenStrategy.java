package com.project.password.manager.auth.token;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.configuration.AuthenticationType;
import com.project.password.manager.model.IToken;
import com.project.password.manager.model.IUser;

public interface SessionTokenStrategy {

	@NotNull
	AuthenticationType getAuthenticationType();

	@NotNull
	String issueToken(@NotNull IUser user, @NotNull SessionTokenRequest request);

	boolean canReuse(@NotNull IToken persistedToken, @NotNull IUser user);

	boolean isValid(@NotNull IToken persistedToken, @NotNull IUser user);
}
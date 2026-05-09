package com.project.password.manager.auth.oauth2;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.model.IUser;

public final class OAuth2Session {

	@NotNull
	private final IUser user;
	@NotNull
	private final String cliToken;

	public OAuth2Session(@NotNull IUser user, @NotNull String cliToken) {
		this.user = user;
		this.cliToken = cliToken;
	}

	@NotNull
	public IUser getUser() {
		return user;
	}

	@NotNull
	public String getCliToken() {
		return cliToken;
	}
}
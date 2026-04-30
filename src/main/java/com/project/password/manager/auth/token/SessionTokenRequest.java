package com.project.password.manager.auth.token;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.project.password.manager.configuration.AuthenticationType;

public final class SessionTokenRequest {

	@NotNull
	private final AuthenticationType authenticationType;
	@Nullable
	private final String sourceToken;

	private SessionTokenRequest(@NotNull AuthenticationType authenticationType, @Nullable String sourceToken) {
		this.authenticationType = authenticationType;
		this.sourceToken = sourceToken;
	}

	@NotNull
	public static SessionTokenRequest jwt() {
		return new SessionTokenRequest(AuthenticationType.JWT, null);
	}

	@NotNull
	public static SessionTokenRequest oauth2(@NotNull String accessToken) {
		return new SessionTokenRequest(AuthenticationType.OAUTH2, accessToken);
	}

	@NotNull
	public AuthenticationType getAuthenticationType() {
		return authenticationType;
	}

	@Nullable
	public String getSourceToken() {
		return sourceToken;
	}
}
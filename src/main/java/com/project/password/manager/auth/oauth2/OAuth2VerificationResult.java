package com.project.password.manager.auth.oauth2;

import java.util.List;

import org.jetbrains.annotations.NotNull;

public final class OAuth2VerificationResult {

	private final boolean valid;
	@NotNull
	private final List<String> missingProperties;
	@NotNull
	private final List<String> scopes;

	public OAuth2VerificationResult(boolean valid, @NotNull List<String> missingProperties,
			@NotNull List<String> scopes) {
		this.valid = valid;
		this.missingProperties = missingProperties;
		this.scopes = scopes;
	}

	public boolean isValid() {
		return valid;
	}

	@NotNull
	public List<String> getMissingProperties() {
		return missingProperties;
	}

	@NotNull
	public List<String> getScopes() {
		return scopes;
	}
}
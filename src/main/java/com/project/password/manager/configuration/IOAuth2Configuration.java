package com.project.password.manager.configuration;

import java.util.List;
import java.util.Map;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface IOAuth2Configuration extends IAuthenticationConfiguration {

	@NotNull
	String DEFAULT_SCOPE = "openid profile email";

	@NotNull
	String SCOPES_DELIMITER = ",";

	@NotNull
	String TOKEN_PARAM_KEY_DELIMITER = "=";

	@NotNull
	String TOKEN_PARAM_ENTRY_DELIMITER = ".";

	@Override
	default AuthenticationType type() {
		return AuthenticationType.OAUTH2;
	}

	@Nullable
	String clientId();

	@Nullable
	String clientSecret();

	@Nullable
	String tokenUrl();

	@Nullable
	String userUrl();

	@Nullable
	String authorizeUrl();

	@Nullable
	String deviceCodeUrl();

	@NotNull
	List<String> scope();

	@NotNull
	Map<String, String> tokenParameters();

}

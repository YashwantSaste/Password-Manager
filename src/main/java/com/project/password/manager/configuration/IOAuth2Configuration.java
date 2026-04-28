package com.project.password.manager.configuration;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface IOAuth2Configuration {

	@NotNull
	String DEFAULT_SCOPE = "openid profile email";

	@NotNull
	String SCOPES_DELIMITER = ",";

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

}

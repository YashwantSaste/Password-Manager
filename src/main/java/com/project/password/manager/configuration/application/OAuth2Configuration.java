package com.project.password.manager.configuration.application;

import java.util.List;
import java.util.Map;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.project.password.manager.configuration.IOAuth2Configuration;

public class OAuth2Configuration implements IOAuth2Configuration {

	@NotNull
	private final PropertiesReader reader;

	public OAuth2Configuration(@NotNull PropertiesReader reader) {
		this.reader = reader;
	}

	@Override
	@Nullable
	public String clientId() {
		return reader.readPropertyAsString(ApplicationProperties.PROPERTY_OAUTH2_CLIENT_ID);
	}

	@Override
	@Nullable
	public String clientSecret() {
		return reader.readPropertyAsString(ApplicationProperties.PROPERTY_OAUTH2_CLIENT_SECRET);
	}

	@Override
	@Nullable
	public String tokenUrl() {
		return reader.readPropertyAsString(ApplicationProperties.PROPERTY_OAUTH2_TOKEN_URL);
	}

	@Override
	@Nullable
	public String userUrl() {
		return reader.readPropertyAsString(ApplicationProperties.PROPERTY_OAUTH2_USER_URL);
	}

	@Override
	@Nullable
	public String authorizeUrl() {
		return reader.readPropertyAsString(ApplicationProperties.PROPERTY_OAUTH2_AUTHORIZE_URL);
	}

	@Override
	@Nullable
	public String deviceCodeUrl() {
		return reader.readPropertyAsString(ApplicationProperties.PROPERTY_OAUTH2_DEVICE_CODE_URL);
	}

	@Override
	@NotNull
	public List<String> scope() {
		return reader.readPropertyAsList(ApplicationProperties.PROPERTY_OAUTH2_SCOPES,
				IOAuth2Configuration.SCOPES_DELIMITER);
	}

	@Override
	@NotNull
	public Map<String, String> tokenParameters() {
		return reader.readPropertyAsMap(ApplicationProperties.PROPERTY_OAUTH2_TOKEN_PARAMTERS,
				TOKEN_PARAM_ENTRY_DELIMITER, TOKEN_PARAM_KEY_DELIMITER);
	}

}

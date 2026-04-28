package com.project.password.manager.auth.oauth2;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.password.manager.util.ModelObjectMapperFactory;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OAuth2AccessToken {

	@JsonProperty("access_token")
	private String accessToken;
	@JsonProperty("refresh_token")
	private String refreshToken;
	@JsonProperty("id_token")
	private String idToken;
	@JsonProperty("token_type")
	private String tokenType;
	@JsonProperty("expires_in")
	private Integer expiresIn;
	@JsonProperty("error")
	private String error;
	@JsonProperty("error_description")
	private String errorDescription;

	public OAuth2AccessToken() {
		// for Jackson
	}

	@NotNull
	public static OAuth2AccessToken parse(@NotNull String body) {
		try {
			return mapper().readValue(body, OAuth2AccessToken.class);
		} catch (JsonProcessingException exception) {
			throw new RuntimeException("Unable to parse the OAuth2 token response.", exception);
		}
	}

	@Nullable
	public String getAccessToken() {
		return accessToken;
	}

	@Nullable
	public String getRefreshToken() {
		return refreshToken;
	}

	@Nullable
	public String getIdToken() {
		return idToken;
	}

	@Nullable
	public String getTokenType() {
		return tokenType;
	}

	@Nullable
	public Integer getExpiresIn() {
		return expiresIn;
	}

	@Nullable
	public String getError() {
		return error;
	}

	@Nullable
	public String getErrorDescription() {
		return errorDescription;
	}

	public boolean hasAccessToken() {
		return accessToken != null && !accessToken.isBlank();
	}

	public boolean hasError() {
		return error != null && !error.isBlank();
	}

	@NotNull
	private static ObjectMapper mapper() {
		return ModelObjectMapperFactory.create();
	}
}
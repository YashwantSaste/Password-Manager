package com.project.password.manager.service;

import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.project.password.manager.auth.oauth2.DeviceCode;
import com.project.password.manager.auth.oauth2.OAuth2AccessToken;
import com.project.password.manager.auth.oauth2.OAuth2Session;
import com.project.password.manager.auth.oauth2.OAuth2UserProfile;
import com.project.password.manager.configuration.IOAuth2Configuration;
import com.project.password.manager.configuration.application.ApplicationProperties;
import com.project.password.manager.model.IUser;
import com.project.password.manager.util.NetworkClient;
import com.project.password.manager.util.NetworkRequestBuilder;
import com.project.password.manager.util.PropertyUtils;
import com.project.password.manager.util.ValidationUtils;

public class OAuth2LoginService {
	private static final int DEFAULT_POLL_INTERVAL_SECONDS = 5;
	private static final int DEFAULT_DEVICE_CODE_EXPIRATION_SECONDS = 600;

	private final IOAuth2Configuration oauth2Configuration;
	private final AuthService authService;
	private final TokenService tokenService;

	private final NetworkClient client = new NetworkClient();

	public OAuth2LoginService(@NotNull IOAuth2Configuration oauth2Configuration, @NotNull AuthService authService,
			@NotNull TokenService tokenService) {
		this.oauth2Configuration = oauth2Configuration;
		this.authService = authService;
		this.tokenService = tokenService;
	}

	@NotNull
	public OAuth2Session login(@NotNull DeviceCode deviceCode) {
		OAuth2AccessToken providerToken = waitForAccessToken(deviceCode);
		OAuth2UserProfile profile = fetchUserProfile(providerToken);
		String userId = resolveUserId(profile);
		String displayName = resolveDisplayName(profile, userId);
		String providerAccessToken = requireRuntimeValue(providerToken.getAccessToken(),
				"OAuth2 login succeeded but the provider did not return an access token.");
		IUser user = authService.loginWithOAuth2Profile(userId, displayName, providerAccessToken);
		String cliToken = requireRuntimeValue(tokenService.getToken(user),
				"OAuth2 login succeeded but no CLI token could be created.");
		return new OAuth2Session(user, cliToken);
	}

	@NotNull
	public DeviceCode initiateLogin() {
		requireConfiguredProperty(oauth2Configuration.clientId(), ApplicationProperties.PROPERTY_OAUTH2_CLIENT_ID);
		requireConfiguredProperty(oauth2Configuration.deviceCodeUrl(),
				ApplicationProperties.PROPERTY_OAUTH2_DEVICE_CODE_URL);
		requireConfiguredProperty(oauth2Configuration.tokenUrl(), ApplicationProperties.PROPERTY_OAUTH2_TOKEN_URL);
		requireConfiguredProperty(oauth2Configuration.userUrl(), ApplicationProperties.PROPERTY_OAUTH2_USER_URL);
		try {
			HttpRequest request = new NetworkRequestBuilder().buildFormRequest(requireConfiguredValue(
					oauth2Configuration.deviceCodeUrl(), ApplicationProperties.PROPERTY_OAUTH2_DEVICE_CODE_URL),
					buildDeviceAuthorizationParameters());
			HttpResponse<String> response = client.send(request);
			ensureSuccess(response, "device code");
			DeviceCode deviceCode = new DeviceCode().parse(response.body());
			if (deviceCode == null) {
				throw new RuntimeException("Authentication server did not return a device-code response.");
			}
			return deviceCode;
		} catch (Exception e) {
			throw new RuntimeException("Could not make request to the authentication server.", e);
		}
	}

	@NotNull
	private OAuth2AccessToken waitForAccessToken(@NotNull DeviceCode deviceCode) {
		String deviceCodeValue = requireRuntimeValue(deviceCode.getDeviceCode(), "Missing OAuth2 value: device_code");
		long deadlineMillis = System.currentTimeMillis()
				+ deviceCode.getExpiresInMillisOrDefault(TimeUnit.SECONDS.toMillis(DEFAULT_DEVICE_CODE_EXPIRATION_SECONDS));
		int pollIntervalSeconds = deviceCode.getPollIntervalSecondsOrDefault(DEFAULT_POLL_INTERVAL_SECONDS);
		while (System.currentTimeMillis() < deadlineMillis) {
			OAuth2AccessToken token = requestAccessToken(deviceCodeValue);
			if (token.hasAccessToken()) {
				return token;
			}
			if (token.hasError()) {
				String error = token.getError();
				if ("authorization_pending".equalsIgnoreCase(error)) {
					sleep(pollIntervalSeconds);
					continue;
				}
				if ("slow_down".equalsIgnoreCase(error)) {
					pollIntervalSeconds = pollIntervalSeconds + DEFAULT_POLL_INTERVAL_SECONDS;
					sleep(pollIntervalSeconds);
					continue;
				}
				throw new RuntimeException(buildOAuth2TokenError(token));
			}
			sleep(pollIntervalSeconds);
		}
		throw new RuntimeException("OAuth2 device login timed out before the provider issued an access token.");
	}

	@NotNull
	private OAuth2AccessToken requestAccessToken(@NotNull String deviceCodeValue) {
		try {
			HttpRequest request = new NetworkRequestBuilder().buildFormRequest(
					requireConfiguredValue(oauth2Configuration.tokenUrl(), ApplicationProperties.PROPERTY_OAUTH2_TOKEN_URL),
					buildTokenRequestParameters(deviceCodeValue));
			HttpResponse<String> response = client.send(request);
			if (response.statusCode() >= 200 && response.statusCode() < 300) {
				return OAuth2AccessToken.parse(response.body());
			}
			OAuth2AccessToken errorResponse = OAuth2AccessToken.parse(response.body());
			if (errorResponse.hasError()) {
				return errorResponse;
			}
			throw new RuntimeException("OAuth2 token endpoint returned HTTP " + response.statusCode() + ": "
					+ response.body());
		} catch (Exception exception) {
			throw new RuntimeException("Could not exchange the device code for an access token.", exception);
		}
	}

	@NotNull
	private OAuth2UserProfile fetchUserProfile(@NotNull OAuth2AccessToken providerToken) {
		String accessToken = requireRuntimeValue(providerToken.getAccessToken(), "Missing OAuth2 value: access_token");
		try {
			HttpRequest request = new NetworkRequestBuilder().buildJsonRequest(
					requireConfiguredValue(oauth2Configuration.userUrl(), ApplicationProperties.PROPERTY_OAUTH2_USER_URL),
					NetworkClient.HTTP_METHOD_GET,
					null, accessToken);
			HttpResponse<String> response = client.send(request);
			ensureSuccess(response, "user info");
			return OAuth2UserProfile.parse(response.body());
		} catch (Exception exception) {
			throw new RuntimeException("Could not fetch the OAuth2 user profile.", exception);
		}
	}

	@NotNull
	private Map<String, String> buildDeviceAuthorizationParameters() {
		Map<String, String> parameters = new LinkedHashMap<>(oauth2Configuration.tokenParameters());
		parameters.putIfAbsent("client_id",
				requireConfiguredValue(oauth2Configuration.clientId(), ApplicationProperties.PROPERTY_OAUTH2_CLIENT_ID));
		parameters.putIfAbsent("scope", String.join(" ", resolveScopes()));
		return parameters;
	}

	@NotNull
	private Map<String, String> buildTokenRequestParameters(@NotNull String deviceCodeValue) {
		Map<String, String> parameters = new LinkedHashMap<>(oauth2Configuration.tokenParameters());
		parameters.put("grant_type", "urn:ietf:params:oauth:grant-type:device_code");
		parameters.put("device_code", deviceCodeValue);
		parameters.put("client_id",
				requireConfiguredValue(oauth2Configuration.clientId(), ApplicationProperties.PROPERTY_OAUTH2_CLIENT_ID));
		String clientSecret = oauth2Configuration.clientSecret();
		if (ValidationUtils.hasText(clientSecret)) {
			parameters.put("client_secret", clientSecret);
		}
		return parameters;
	}

	@NotNull
	private List<String> resolveScopes() {
		List<String> scopes = oauth2Configuration.scope();
		if (scopes.isEmpty()) {
			return List.of(IOAuth2Configuration.DEFAULT_SCOPE.split(" "));
		}
		return scopes;
	}

	@NotNull
	private String resolveUserId(@NotNull OAuth2UserProfile profile) {
		String preferredUsername = profile.getPreferredUsername();
		if (ValidationUtils.hasText(preferredUsername)) {
			return preferredUsername.trim();
		}
		String email = profile.getEmail();
		if (ValidationUtils.hasText(email)) {
			return email.trim();
		}
		return requireRuntimeValue(profile.getSubject(), "Missing OAuth2 value: sub");
	}

	@NotNull
	private String resolveDisplayName(@NotNull OAuth2UserProfile profile, @NotNull String fallbackUserId) {
		String name = profile.getName();
		if (ValidationUtils.hasText(name)) {
			return name.trim();
		}
		return fallbackUserId;
	}

	private void ensureSuccess(@NotNull HttpResponse<String> response, @NotNull String endpointName) {
		if (response.statusCode() >= 200 && response.statusCode() < 300) {
			return;
		}
		String details = response.body();
		HttpHeaders headers = response.headers();
		throw new RuntimeException("OAuth2 " + endpointName + " endpoint returned HTTP " + response.statusCode()
		+ (!ValidationUtils.hasText(details) ? "" : ": " + details)
		+ (headers.map().isEmpty() ? "" : " | headers=" + headers.map()));
	}

	private void sleep(int pollIntervalSeconds) {
		try {
			TimeUnit.SECONDS.sleep(Math.max(1, pollIntervalSeconds));
		} catch (InterruptedException exception) {
			Thread.currentThread().interrupt();
			throw new RuntimeException("OAuth2 device login was interrupted.", exception);
		}
	}

	@NotNull
	private String requireConfiguredValue(@Nullable String value, @NotNull String label) {
		return requireConfiguredProperty(value, label);
	}

	@NotNull
	private String requireConfiguredProperty(@Nullable String value, @NotNull String propertyName) {
		try {
			return PropertyUtils.requireProperty(value, propertyName, "OAuth2");
		} catch (IllegalStateException exception) {
			throw new RuntimeException(exception.getMessage(), exception);
		}
	}

	@NotNull
	private String requireRuntimeValue(@Nullable String value, @NotNull String message) {
		try {
			return ValidationUtils.requireText(value, message);
		} catch (IllegalArgumentException exception) {
			throw new RuntimeException(exception.getMessage(), exception);
		}
	}

	@NotNull
	private String buildOAuth2Error(@NotNull String prefix, @Nullable String error, @Nullable String description) {
		StringBuilder builder = new StringBuilder(prefix);
		if (ValidationUtils.hasText(error)) {
			builder.append(" [").append(error).append(']');
		}
		if (ValidationUtils.hasText(description)) {
			builder.append(": ").append(description);
		}
		return builder.toString();
	}

	@NotNull
	private String buildOAuth2TokenError(@NotNull OAuth2AccessToken token) {
		String message = buildOAuth2Error("OAuth2 token request failed", token.getError(), token.getErrorDescription());
		if (!"invalid_client".equalsIgnoreCase(token.getError())) {
			return message;
		}

		String tokenUrl = oauth2Configuration.tokenUrl();
		boolean microsoftEntraTokenEndpoint = tokenUrl != null && tokenUrl.contains("login.microsoftonline.com");
		boolean hasClientSecret = ValidationUtils.hasText(oauth2Configuration.clientSecret());

		if (microsoftEntraTokenEndpoint) {
			return message
					+ " | Diagnostic: the request already includes client_id"
					+ (hasClientSecret ? " and a configured client_secret" : " but no configured client_secret")
					+ ". Microsoft Entra device-code flow expects a public client by default. If this app registration is configured as confidential, enable 'Allow public client flows' and clear "
					+ ApplicationProperties.PROPERTY_OAUTH2_CLIENT_SECRET
					+ ", or verify that the configured secret is valid for the same app registration and tenant.";
		}

		return message
				+ (hasClientSecret
						? " | Diagnostic: a client_secret is configured locally, so verify that the provider accepts device-code flow for confidential clients and that the secret is valid."
								: " | Diagnostic: no client_secret is configured locally, so the provider may require a confidential-client token exchange.");
	}

}

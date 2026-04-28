package com.project.password.manager.auth.oauth2;

import java.util.concurrent.TimeUnit;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.password.manager.util.ModelObjectMapperFactory;

public class DeviceCode {

	@JsonProperty("device_code")
	private String deviceCode;
	@JsonProperty("user_code")
	private String userCode;
	@JsonProperty("verification_uri")
	private String verificationUrl;
	@JsonProperty("expires_in")
	private String expiresIn;
	@JsonProperty("interval")
	private String interval;
	@JsonProperty("message")
	private String message;

	public DeviceCode() {
		// for Jackson
	}

	@Nullable
	public DeviceCode parse(@NotNull String body) {
		try {
			return getMapper().readValue(body, DeviceCode.class);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Unable to parse the IDP response.");
		}
	}

	@Nullable
	public String getDeviceCode() {
		return deviceCode;
	}

	@Nullable
	public String getUserCode() {
		return userCode;
	}

	@Nullable
	public String getVerificationUrl() {
		return verificationUrl;
	}

	@Nullable
	public String getExpiresIn() {
		return expiresIn;
	}

	@Nullable
	public String getInterval() {
		return interval;
	}

	@Nullable
	public String getMessage() {
		return message;
	}

	public int getExpiresInSecondsOrDefault(int defaultValue) {
		return parseInteger(expiresIn, defaultValue);
	}

	public int getPollIntervalSecondsOrDefault(int defaultValue) {
		return parseInteger(interval, defaultValue);
	}

	public long getExpiresInMillisOrDefault(long defaultValue) {
		return TimeUnit.SECONDS.toMillis(getExpiresInSecondsOrDefault((int) TimeUnit.MILLISECONDS.toSeconds(defaultValue)));
	}

	@NotNull
	protected ObjectMapper getMapper() {
		return ModelObjectMapperFactory.create();
	}

	private int parseInteger(@Nullable String value, int defaultValue) {
		if (value == null || value.isBlank()) {
			return defaultValue;
		}
		try {
			return Integer.parseInt(value.trim());
		} catch (NumberFormatException exception) {
			return defaultValue;
		}
	}
}

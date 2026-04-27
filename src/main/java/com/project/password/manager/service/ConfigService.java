package com.project.password.manager.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.project.password.manager.cli.runtime.CliTheme;
import com.project.password.manager.configuration.application.ApplicationProperties;
import com.project.password.manager.configuration.application.PropertiesReader;

public class ConfigService {

	private static final DateTimeFormatter AUDIT_TIME_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

	@NotNull
	public Map<String, String> getProperties(boolean showSensitive) {
		Properties properties = loadProperties();
		Map<String, String> values = new LinkedHashMap<>();
		for (String key : ApplicationProperties.supportedKeys()) {
			values.put(key, displayValue(key, properties.getProperty(key), showSensitive));
		}
		return values;
	}

	@NotNull
	public String getProperty(@NotNull String key, boolean showSensitive) {
		validateKey(key);
		Properties properties = loadProperties();
		return displayValue(key, properties.getProperty(key), showSensitive);
	}

	public void updateProperty(@NotNull String key, @NotNull String value, @Nullable String comment, @NotNull String actor) {
		validateKey(key);
		validateValue(key, value);

		Properties properties = loadProperties();
		properties.setProperty(key, value);
		storeProperties(properties, buildAuditComment(comment, actor));
		PropertiesReader.refreshIfInitialized();

		if (ApplicationProperties.PROPERTY_APP_CLI_THEME.equals(key)) {
			CliTheme.initialize();
		}
	}

	@NotNull
	private Properties loadProperties() {
		try {
			return PropertiesReader.loadProperties();
		} catch (IOException ioException) {
			throw new RuntimeException("Failed to read configuration properties", ioException);
		}
	}

	private void storeProperties(@NotNull Properties properties, @NotNull String comment) {
		try {
			PropertiesReader.storeProperties(properties, comment);
		} catch (IOException ioException) {
			throw new RuntimeException("Failed to persist configuration property", ioException);
		}
	}

	private void validateKey(@NotNull String key) {
		if (!ApplicationProperties.isSupportedKey(key)) {
			throw new IllegalArgumentException("Unsupported configuration key: " + key);
		}
	}

	private void validateValue(@NotNull String key, @Nullable String value) {
		if (value == null || value.isBlank()) {
			throw new IllegalArgumentException("Configuration value cannot be blank for key: " + key);
		}
		if (ApplicationProperties.PROPERTY_APP_CLI_THEME.equals(key) && !CliTheme.isSupportedTheme(value)) {
			throw new IllegalArgumentException("Unsupported theme: " + value);
		}
	}

	@NotNull
	private String buildAuditComment(@Nullable String comment, @NotNull String actor) {
		String baseComment = (comment == null || comment.isBlank()) ? "Configuration updated" : comment.trim();
		String formattedTimestamp = LocalDateTime.now().format(AUDIT_TIME_FORMAT);
		return baseComment + " Updated by user: [" + actor + "] at " + formattedTimestamp;
	}

	@NotNull
	private String displayValue(@NotNull String key, @Nullable String value, boolean showSensitive) {
		if (value == null || value.isBlank()) {
			return "-";
		}
		if (!showSensitive && ApplicationProperties.isSensitiveKey(key)) {
			return "********";
		}
		return value;
	}
}
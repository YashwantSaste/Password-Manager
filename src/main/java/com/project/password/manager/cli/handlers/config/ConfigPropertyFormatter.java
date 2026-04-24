package com.project.password.manager.cli.handlers.config;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.project.password.manager.configuration.application.ApplicationProperties;

final class ConfigPropertyFormatter {

	private ConfigPropertyFormatter() {
	}

	@NotNull
	static String displayValue(@NotNull String key, @Nullable String value, boolean showSensitive) {
		if (value == null || value.isBlank()) {
			return "-";
		}
		if (!showSensitive && ApplicationProperties.isSensitiveKey(key)) {
			return "********";
		}
		return value;
	}
}
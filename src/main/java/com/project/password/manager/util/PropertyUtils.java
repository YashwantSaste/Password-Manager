package com.project.password.manager.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class PropertyUtils {

	private PropertyUtils() {
	}

	@NotNull
	public static String requireProperty(@Nullable String value, @NotNull String propertyName,
			@NotNull String owner) {
		try {
			return ValidationUtils.requireText(value,
					owner + " configuration is missing required property: " + propertyName);
		} catch (IllegalArgumentException exception) {
			throw new IllegalStateException(exception.getMessage(), exception);
		}
	}
}
package com.project.password.manager.configuration;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.project.password.manager.util.ValidationUtils;

public enum AuthenticationType {
	JWT("jwt"),
	OAUTH2("oauth2"),
	SAML("saml");

	@NotNull
	private final String storageValue;

	AuthenticationType(@NotNull String storageValue) {
		this.storageValue = storageValue;
	}

	@NotNull
	public String value() {
		return storageValue;
	}

	@NotNull
	public static AuthenticationType fromValue(@Nullable String value) {
		ValidationUtils.requireText(value, "Authentication method type value must not be null.");
		for (AuthenticationType type : values()) {
			if (type.storageValue.equalsIgnoreCase(value.trim())) {
				return type;
			}
		}
		return JWT;
	}
}
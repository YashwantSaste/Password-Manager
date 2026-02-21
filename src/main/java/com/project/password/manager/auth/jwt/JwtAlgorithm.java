package com.project.password.manager.auth.jwt;

import org.jetbrains.annotations.NotNull;

public enum JwtAlgorithm {
	HS256, HS384, HS512, RS256, RS384, RS512, ES256, ES384, ES512;

	@NotNull
	public static JwtAlgorithm from(@NotNull String value) {
		return JwtAlgorithm.valueOf(value.toUpperCase());
	}
}

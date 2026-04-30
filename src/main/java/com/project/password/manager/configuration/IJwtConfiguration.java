package com.project.password.manager.configuration;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.project.password.manager.auth.jwt.JwtAlgorithm;

public interface IJwtConfiguration extends IAuthenticationConfiguration {

	@Override
	default AuthenticationType type() {
		return AuthenticationType.JWT;
	}

	/** Algorithm: HS256, RS256, ES256, etc */
	@NotNull
	JwtAlgorithm algorithm();

	/** Token issuer */
	@NotNull
	String issuer();

	/** Access token expiration in milliseconds */
	long accessExpirationMs();

	/** Refresh token expiration in milliseconds */
	long refreshExpirationMs();

	/** Secret for HMAC algorithms (HS256, etc) */
	@Nullable
	String secret();

	/** Private key path (for RSA / ECDSA signing) */
	@Nullable
	String privateKeyPath();

	/** Public key path (for RSA / ECDSA verification) */
	@Nullable
	String publicKeyPath();
}

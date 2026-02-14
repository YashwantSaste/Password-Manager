package com.project.password.manager.auth.jwt;

import org.jetbrains.annotations.NotNull;

import com.auth0.jwt.algorithms.Algorithm;
import com.project.password.manager.configuration.IJwtConfiguration;

public class JwtAlgorithmFactory {

	@NotNull
	private final IJwtConfiguration jwtConfiguration;

	public JwtAlgorithmFactory(@NotNull IJwtConfiguration jwtConfiguration) {
		this.jwtConfiguration = jwtConfiguration;
	}

	public Algorithm createAlgorithm() throws Exception {

		switch (jwtConfiguration.algorithm()) {

		case HS256:
			validateSecret();
			return Algorithm.HMAC256(jwtConfiguration.secret());

		case HS384:
			validateSecret();
			return Algorithm.HMAC384(jwtConfiguration.secret());

		case HS512:
			validateSecret();
			return Algorithm.HMAC512(jwtConfiguration.secret());

		case RS256:
			return Algorithm.RSA256(KeyLoader.loadRsaPublicKey(jwtConfiguration.publicKeyPath()),
					KeyLoader.loadRsaPrivateKey(jwtConfiguration.privateKeyPath()));

		case RS384:
			return Algorithm.RSA384(KeyLoader.loadRsaPublicKey(jwtConfiguration.publicKeyPath()),
					KeyLoader.loadRsaPrivateKey(jwtConfiguration.privateKeyPath()));

		case RS512:
			return Algorithm.RSA512(KeyLoader.loadRsaPublicKey(jwtConfiguration.publicKeyPath()),
					KeyLoader.loadRsaPrivateKey(jwtConfiguration.privateKeyPath()));

		case ES256:
			return Algorithm.ECDSA256(KeyLoader.loadEcPublicKey(jwtConfiguration.publicKeyPath()),
					KeyLoader.loadEcPrivateKey(jwtConfiguration.privateKeyPath()));

		case ES384:
			return Algorithm.ECDSA384(KeyLoader.loadEcPublicKey(jwtConfiguration.publicKeyPath()),
					KeyLoader.loadEcPrivateKey(jwtConfiguration.privateKeyPath()));

		case ES512:
			return Algorithm.ECDSA512(KeyLoader.loadEcPublicKey(jwtConfiguration.publicKeyPath()),
					KeyLoader.loadEcPrivateKey(jwtConfiguration.privateKeyPath()));

		default:
			throw new IllegalArgumentException("Unsupported JWT Algorithm: " + jwtConfiguration.algorithm());
		}
	}

	private void validateSecret() {
		if (jwtConfiguration.secret() == null || jwtConfiguration.secret().isBlank()) {
			throw new IllegalStateException("JWT secret must be configured for HMAC algorithms");
		}
	}

}

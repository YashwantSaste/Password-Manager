package com.project.password.manager.auth.token;

import java.util.Date;

import org.jetbrains.annotations.NotNull;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.project.password.manager.auth.jwt.JwtAlgorithmFactory;
import com.project.password.manager.configuration.AuthenticationType;
import com.project.password.manager.configuration.IJwtConfiguration;
import com.project.password.manager.model.IToken;
import com.project.password.manager.model.IUser;

public final class JwtSessionTokenStrategy implements SessionTokenStrategy {

	@NotNull
	private final IJwtConfiguration jwtConfiguration;
	@NotNull
	private final Algorithm algorithm;

	public JwtSessionTokenStrategy(@NotNull IJwtConfiguration jwtConfiguration) {
		this.jwtConfiguration = jwtConfiguration;
		this.algorithm = new JwtAlgorithmFactory(jwtConfiguration).createAlgorithm();
	}

	@Override
	@NotNull
	public AuthenticationType getAuthenticationType() {
		return AuthenticationType.JWT;
	}

	@Override
	@NotNull
	public String issueToken(@NotNull IUser user, @NotNull SessionTokenRequest request) {
		return JWT.create()
				.withIssuer(jwtConfiguration.issuer())
				.withSubject(user.getId())
				.withIssuedAt(new Date())
				.withExpiresAt(new Date(System.currentTimeMillis() + jwtConfiguration.accessExpirationMs()))
				.sign(algorithm);
	}

	@Override
	public boolean canReuse(@NotNull IToken persistedToken, @NotNull IUser user) {
		return getAuthenticationType().value().equalsIgnoreCase(persistedToken.getTokenType())
				&& isValid(persistedToken, user);
	}

	@Override
	public boolean isValid(@NotNull IToken persistedToken, @NotNull IUser user) {
		try {
			JWTVerifier verifier = JWT.require(algorithm)
					.withIssuer(jwtConfiguration.issuer())
					.withSubject(user.getId())
					.build();
			return verifier.verify(persistedToken.getToken()) != null;
		} catch (JWTVerificationException exception) {
			return false;
		}
	}
}
package com.project.password.manager.service;

import java.util.Date;
import java.util.UUID;

import org.jetbrains.annotations.NotNull;

import com.auth0.jwt.JWT;
import com.project.password.manager.auth.jwt.JwtAlgorithmFactory;
import com.project.password.manager.configuration.IJwtConfiguration;
import com.project.password.manager.model.IUser;

public class TokenService implements IService {

	@NotNull
	private final IJwtConfiguration jwtConfiguration;

	public TokenService(@NotNull IJwtConfiguration jwtConfiguration) {
		this.jwtConfiguration = jwtConfiguration;
	}

	public String createToken(@NotNull IUser user) {
		try {
			return JWT.create()
					.withIssuer(jwtConfiguration.issuer())
					.withSubject(user.getId())
					.withJWTId(UUID.randomUUID().toString())
					.withIssuedAt(new Date())
					.withExpiresAt(new Date(System.currentTimeMillis() + jwtConfiguration.accessExpirationMs()))
					.sign(new JwtAlgorithmFactory(jwtConfiguration).createAlgorithm());
		} catch (Exception ex) {
			throw new RuntimeException("Error while generating token: " + ex);
		}

	}
}

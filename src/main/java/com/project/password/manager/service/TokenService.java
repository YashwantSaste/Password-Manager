package com.project.password.manager.service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.project.password.manager.auth.jwt.JwtAlgorithmFactory;
import com.project.password.manager.configuration.IJwtConfiguration;
import com.project.password.manager.configuration.application.Configuration;
import com.project.password.manager.database.DataRepository;
import com.project.password.manager.database.DataRepositoryFactory;
import com.project.password.manager.model.IToken;
import com.project.password.manager.model.IUser;

public class TokenService implements IService {

	private final Cache<String, String> tokenCacheFor = Caffeine.newBuilder()
			.expireAfterWrite(1, TimeUnit.MINUTES).maximumSize(100).build();
	@NotNull
	private final IJwtConfiguration jwtConfiguration;
	@NotNull
	private final DataRepository<IToken, String> tokenRepo;
	@NotNull
	private final Algorithm algorithm;

	public TokenService(@NotNull IJwtConfiguration jwtConfiguration) {
		this.jwtConfiguration = jwtConfiguration;
		this.algorithm = new JwtAlgorithmFactory(jwtConfiguration).createAlgorithm();
		this.tokenRepo = new DataRepositoryFactory(Configuration.getInstance().databaseConfiguration())
				.getRepository(IToken.class, String.class);
	}

	@NotNull
	public String createToken(@NotNull IUser user) {
		String tokenIfAlreadyExists = getToken(user);
		if ((tokenIfAlreadyExists) != null && verify(tokenIfAlreadyExists, user) != null) {
			return tokenIfAlreadyExists;
		}
		String token = JWT.create()
				.withIssuer(jwtConfiguration.issuer())
				.withSubject(user.getId())
				.withIssuedAt(new Date())
				.withExpiresAt(new Date(System.currentTimeMillis() + jwtConfiguration.accessExpirationMs()))
				.sign(algorithm);
		tokenCacheFor.put(user.getId(), token);
		return token;
	}

	@Nullable
	public DecodedJWT verify(@NotNull String token, @NotNull IUser user) {
		JWTVerifier verifier = JWT.require(algorithm)
				.withIssuer(jwtConfiguration.issuer())
				.withSubject(user.getId())
				.build();
		return verifier.verify(token);
	}

	@Nullable
	public String getToken(@NotNull IUser user) {
		String userId = user.getId();
		String cachedToken = tokenCacheFor.getIfPresent(userId);
		if (cachedToken != null) {
			return cachedToken;
		}
		IToken persistedToken = tokenRepo.findById(userId);
		if (persistedToken == null) {
			return null;
		}
		String token = persistedToken.getToken();
		if (token != null) {
			tokenCacheFor.put(userId, token);
		}
		return token;
	}

	public void saveToken(@NotNull IUser user, @NotNull IToken token) {
		String userId = user.getId();
		tokenCacheFor.put(userId, token.getToken());
		if (tokenRepo.findById(userId) == null) {
			tokenRepo.save(token);
			return;
		}
		tokenRepo.update(userId, token);
	}

}

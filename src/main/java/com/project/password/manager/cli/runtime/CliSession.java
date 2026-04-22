package com.project.password.manager.cli.runtime;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.google.inject.Singleton;
import com.project.password.manager.exceptions.UnauthorizedSessionException;

@Singleton
public class CliSession {

	@Nullable
	private String userId;
	@Nullable
	private String token;

	public synchronized void open(@NotNull String userId, @NotNull String token) {
		this.userId = userId;
		this.token = token;
	}

	public synchronized void clear() {
		this.userId = null;
		this.token = null;
	}

	public synchronized boolean isAuthenticated() {
		return userId != null && !userId.isBlank() && token != null && !token.isBlank();
	}

	@NotNull
	public synchronized String requireUserId() {
		if (userId == null || userId.isBlank()) {
			throw new UnauthorizedSessionException("No active session. Please login first.");
		}
		return userId;
	}

	@NotNull
	public synchronized String requireToken() {
		if (token == null || token.isBlank()) {
			throw new UnauthorizedSessionException("No active session. Please login first.");
		}
		return token;
	}
}
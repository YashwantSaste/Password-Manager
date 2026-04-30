package com.project.password.manager.auth.token;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.project.password.manager.configuration.AuthenticationType;

public final class SessionTokenStrategyRegistry {

	@NotNull
	private final Map<AuthenticationType, SessionTokenStrategy> strategiesByType;

	public SessionTokenStrategyRegistry(@NotNull List<SessionTokenStrategy> strategies) {
		this.strategiesByType = strategies.stream()
				.collect(Collectors.toUnmodifiableMap(SessionTokenStrategy::getAuthenticationType, Function.identity()));
	}

	@Nullable
	public SessionTokenStrategy find(@NotNull AuthenticationType authenticationType) {
		return strategiesByType.get(authenticationType);
	}

	@NotNull
	public SessionTokenStrategy require(@NotNull AuthenticationType authenticationType) {
		SessionTokenStrategy strategy = find(authenticationType);
		if (strategy == null) {
			throw new IllegalStateException(
					"No session token strategy is registered for authentication type: " + authenticationType);
		}
		return strategy;
	}
}
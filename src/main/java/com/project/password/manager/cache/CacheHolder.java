package com.project.password.manager.cache;

import jakarta.validation.constraints.NotNull;

import com.github.benmanes.caffeine.cache.Cache;

public final class CacheHolder<K, V> {

	@NotNull
	private final CacheDefinition<K, V> definition;
	@NotNull
	private final Cache<K, V> cache;

	CacheHolder(@NotNull CacheDefinition<K, V> definition, @NotNull Cache<K, V> cache) {
		this.definition = definition;
		this.cache = cache;
	}

	@NotNull
	CacheDefinition<K, V> getDefinition() {
		return definition;
	}

	@NotNull
	Cache<K, V> getCache() {
		return cache;
	}
}
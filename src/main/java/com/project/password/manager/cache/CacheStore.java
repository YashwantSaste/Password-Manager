package com.project.password.manager.cache;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.github.benmanes.caffeine.cache.Cache;

public class CacheStore implements ICacheStore {

	private final Map<String, CacheHolder<?, ?>> caches = new ConcurrentHashMap<>();

	@Override
	@NotNull
	public Set<String> availableCaches() {
		return Collections.unmodifiableSet(caches.keySet());
	}

	@Override
	public <K, V> void createCache(@NotNull CacheDefinition<K, V> cacheDefinition) {
		getOrCreateCache(cacheDefinition);
	}

	@Override
	public <K, V> void put(@NotNull CacheDefinition<K, V> cacheDefinition, @NotNull K key, @NotNull V value) {
		getOrCreateCache(cacheDefinition).put(key, value);
	}

	@Override
	public @Nullable <K, V> V get(@NotNull CacheDefinition<K, V> cacheDefinition, @NotNull K key) {
		return getOrCreateCache(cacheDefinition).getIfPresent(key);
	}

	@Override
	public <K, V> void invalidate(@NotNull CacheDefinition<K, V> cacheDefinition, @NotNull K key) {
		getOrCreateCache(cacheDefinition).invalidate(key);
	}

	@Override
	public <K, V> void clear(@NotNull CacheDefinition<K, V> cacheDefinition) {
		getOrCreateCache(cacheDefinition).invalidateAll();
	}

	@Override
	public void clearAll() {
		for (CacheHolder<?, ?> holder : caches.values()) {
			holder.getCache().invalidateAll();
		}
		caches.clear();
	}

	@SuppressWarnings("unchecked")
	private <K, V> Cache<K, V> getOrCreateCache(@NotNull CacheDefinition<K, V> cacheDefinition) {
		CacheHolder<?, ?> existingHolder = caches.compute(cacheDefinition.getName(), (name, existing) -> {
			if (existing == null) {
				Cache<K, V> cache = new CacheBuilder<K, V>().build();
				return new CacheHolder<>(cacheDefinition, cache);
			}

			validateCacheDefinitionCompatibility(cacheDefinition, existing);
			return existing;
		});
		return ((CacheHolder<K, V>) existingHolder).getCache();
	}

	private <K, V> void validateCacheDefinitionCompatibility(@NotNull CacheDefinition<K, V> requestedDefinition,
			@NotNull CacheHolder<?, ?> existingHolder) {
		CacheDefinition<?, ?> existingDefinition = existingHolder.getDefinition();

		boolean sameKeyType = existingDefinition.getKeyType().equals(requestedDefinition.getKeyType());
		boolean sameValueType = existingDefinition.getValueType().equals(requestedDefinition.getValueType());

		if (!sameKeyType || !sameValueType) {
			throw new IllegalStateException("Cache '" + requestedDefinition.getName()
			+ "' already exists with incompatible types. " + "Existing: <"
			+ existingDefinition.getKeyType().getName() + ", " + existingDefinition.getValueType().getName()
			+ ">, requested: <" + requestedDefinition.getKeyType().getName() + ", "
			+ requestedDefinition.getValueType().getName() + ">");
		}
	}
}
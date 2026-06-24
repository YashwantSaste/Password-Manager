package com.project.password.manager.cache;

import java.util.List;

import javax.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import com.github.benmanes.caffeine.cache.Cache;

public interface ICacheStore {

	@NotNull
	List<String> availableCaches();

	@Nullable
	<K, V> Cache<K, V> getCache(@NotNull CacheDefinition<K, V> cacheDefinition);

	<K, V> void createCache(@NotNull CacheDefinition<K, V> cacheDefinition);

	<K, V> void put(@NotNull CacheDefinition<K, V> cacheDefinition, @NotNull K key, @NotNull V value);

	@Nullable
	<K, V> V get(@NotNull CacheDefinition<K, V> cacheDefinition, @NotNull K key);

	<K, V> void invalidate(@NotNull CacheDefinition<K, V> cacheDefinition, @NotNull K key);

	<K, V> void clear(@NotNull CacheDefinition<K, V> cacheDefinition);

	void clearAll();
}
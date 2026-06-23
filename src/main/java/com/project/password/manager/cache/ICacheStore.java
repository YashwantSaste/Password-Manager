package com.project.password.manager.cache;

import java.util.Set;

import javax.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

public interface ICacheStore {

	@NotNull
	Set<String> availableCaches();

	<K, V> void createCache(@NotNull CacheDefinition<K, V> cacheDefinition);

	<K, V> void put(@NotNull CacheDefinition<K, V> cacheDefinition, @NotNull K key, @NotNull V value);

	@Nullable
	<K, V> V get(@NotNull CacheDefinition<K, V> cacheDefinition, @NotNull K key);

	<K, V> void invalidate(@NotNull CacheDefinition<K, V> cacheDefinition, @NotNull K key);

	<K, V> void clear(@NotNull CacheDefinition<K, V> cacheDefinition);

	void clearAll();
}
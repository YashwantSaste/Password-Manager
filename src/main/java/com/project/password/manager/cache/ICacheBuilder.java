package com.project.password.manager.cache;

import org.jetbrains.annotations.NotNull;

import com.github.benmanes.caffeine.cache.Cache;

public interface ICacheBuilder<K, V> {

	@NotNull
	Cache<K, V> build();
}
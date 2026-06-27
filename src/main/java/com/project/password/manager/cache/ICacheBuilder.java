package com.project.password.manager.cache;

import jakarta.validation.constraints.NotNull;

import com.github.benmanes.caffeine.cache.Cache;

public interface ICacheBuilder<K, V> {

	@NotNull
	Cache<K, V> build();
}
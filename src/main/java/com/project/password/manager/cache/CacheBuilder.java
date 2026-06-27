package com.project.password.manager.cache;

import java.time.Duration;

import jakarta.validation.constraints.NotNull;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

public class CacheBuilder<K, V> implements ICacheBuilder<K, V> {

	private long maximumSize = 1000;
	private Duration expireAfterWrite = Duration.ofMinutes(30);

	public CacheBuilder<K, V> maximumSize(long maximumSize) {
		this.maximumSize = maximumSize;
		return this;
	}

	public CacheBuilder<K, V> expireAfterWrite(@NotNull Duration duration) {
		this.expireAfterWrite = duration;
		return this;
	}

	@Override
	@NotNull
	public Cache<K, V> build() {
		return Caffeine.newBuilder().maximumSize(maximumSize).expireAfterWrite(expireAfterWrite).build();
	}
}

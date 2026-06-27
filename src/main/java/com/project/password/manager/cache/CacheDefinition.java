package com.project.password.manager.cache;


import jakarta.validation.constraints.NotNull;

public final class CacheDefinition<K, V> {

	@NotNull
	private final String name;
	@NotNull
	private final Class<K> keyType;
	@NotNull
	private final Class<V> valueType;

	public CacheDefinition(@NotNull String name, @NotNull Class<K> keyType, @NotNull Class<V> valueType) {
		this.name = name;
		this.keyType = keyType;
		this.valueType = valueType;
	}

	@NotNull
	public String getName() {
		return name;
	}

	@NotNull
	public Class<K> getKeyType() {
		return keyType;
	}

	@NotNull
	public Class<V> getValueType() {
		return valueType;
	}

}
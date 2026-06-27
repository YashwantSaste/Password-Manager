package com.project.password.manager.configuration.application;

import jakarta.validation.constraints.NotNull;

import com.project.password.manager.configuration.ICacheConfiguration;

public class CacheConfiguration implements ICacheConfiguration {

	@NotNull
	private final PropertiesReader reader;

	public CacheConfiguration(@NotNull PropertiesReader reader) {
		this.reader = reader;
	}

	@Override
	public long maximumCacheSize() {
		return reader.readPropertyAsLong(ApplicationProperties.PROPERTY_APP_CACHE_MAXIMUM_SIZE, 100);
	}

	@Override
	public long cacheExpiryDuration() {
		return reader.readPropertyAsLong(ApplicationProperties.PROPERTY_APP_CACHE_EXPIRY_DURATION, 60000);
	}

}

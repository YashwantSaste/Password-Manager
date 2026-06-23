package com.project.password.manager.cache;

import java.time.Duration;

import com.github.benmanes.caffeine.cache.Cache;
import com.project.password.manager.configuration.ICacheConfiguration;
import com.project.password.manager.configuration.application.Configuration;
import com.project.password.manager.model.IUser;

public class UserCacheBuilder implements ICacheBuilder<String, IUser> {

	private ICacheConfiguration cacheConfiguration = Configuration.getInstance().cacheConfiguration();

	@Override
	public Cache<String, IUser> build() {
		return new CacheBuilder<String, IUser>()
				.expireAfterWrite(Duration.ofMillis(cacheConfiguration.cacheExpiryDuration()))
				.maximumSize(cacheConfiguration.maximumCacheSize()).build();
	}

	public void invalidateUserCache() {

	}

}

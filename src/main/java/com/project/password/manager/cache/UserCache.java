package com.project.password.manager.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jetbrains.annotations.NotNull;

import com.github.benmanes.caffeine.cache.Cache;
import com.project.password.manager.model.IUser;

public class UserCache extends CacheBuilder<String, IUser> {

	@NotNull
	private ICacheStore cacheStore = new CacheStore();

	@NotNull
	public static final CacheDefinition<String, IUser> USER_CACHE = new CacheDefinition<>("user-cache", String.class,
			IUser.class);


	public void invalidateUserFromCache(@NotNull String userId) {
		cacheStore.invalidate(USER_CACHE, userId);
	}

	public void addUserToCache(@NotNull IUser user) {
		cacheStore.put(USER_CACHE, user.getId(), user);
	}

	@NotNull
	public List<IUser> getCachedUsers(){
		List<IUser> user = new ArrayList<>();
		for (Map.Entry<String, IUser> entry : getUserCacheFromStore().asMap().entrySet()) {
			user.add(entry.getValue());
		}
		return user;
	}

	@NotNull
	public void addUsersToCache(@NotNull List<IUser> users) {
		Cache<String, IUser> userCache = getUserCacheFromStore();
		for (IUser user : users) {
			userCache.put(user.getId(), user);
		}
	}

	@NotNull
	private Cache<String, IUser> getUserCacheFromStore() {
		return cacheStore.getCache(USER_CACHE);
	}
}

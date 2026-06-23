package com.project.password.manager.user.management;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.jetbrains.annotations.NotNull;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.project.password.manager.model.IUser;
import com.project.password.manager.model.UserRole;
import com.project.password.manager.service.UserService;

public class UserPool implements IUserPool {

	private final Cache<String, IUser> userCache = Caffeine.newBuilder().expireAfterWrite(1, TimeUnit.MINUTES)
			.maximumSize(100).build();

	@NotNull
	private UserService userService;

	public UserPool(@NotNull UserService userService) {
		this.userService = userService;
		loadUsersInCache();
	}

	@Override
	@NotNull
	public List<IUser> getAdminUsers() {
		return getAllUsers().stream().filter(user -> user.getRoles().contains(UserRole.ADMIN)).distinct().toList();
	}

	@Override
	@NotNull
	public List<IUser> getNonAdminUsers() {
		return getAllUsers().stream().filter(user -> !user.getRoles().contains(UserRole.ADMIN)).distinct().toList();
	}

	@Override
	@NotNull
	public List<IUser> getAllUsers() {
		return userService.getUsers();
	}

	private void loadUsersInCache() {
		for (IUser user : getAllUsers()) {
			userCache.put(user.getId(), user);
		}
	}
}

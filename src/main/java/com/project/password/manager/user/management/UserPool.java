package com.project.password.manager.user.management;

import java.util.List;

import jakarta.validation.constraints.NotNull;

import com.project.password.manager.cache.UserCache;
import com.project.password.manager.model.IUser;
import com.project.password.manager.model.UserRole;
import com.project.password.manager.service.UserService;

public class UserPool implements IUserPool {

	@NotNull
	private UserService userService;

	@NotNull
	private UserCache cache = new UserCache();

	public UserPool(@NotNull UserService userService) {
		this.userService = userService;
		loadUsersInCache();
	}

	@Override
	@NotNull
	public List<IUser> getAdminUsers() {
		return loadUsersFromCache().stream().filter(user -> user.getRoles().contains(UserRole.ADMIN)).distinct()
				.toList();
	}

	@Override
	@NotNull
	public List<IUser> getNonAdminUsers() {
		return loadUsersFromCache().stream().filter(user -> !user.getRoles().contains(UserRole.ADMIN)).distinct()
				.toList();
	}

	@Override
	@NotNull
	public List<IUser> getAllUsers() {
		return userService.getUsers();
	}

	private List<IUser> loadUsersFromCache() {
		return cache.getCachedUsers();
	}

	private void loadUsersInCache() {
		cache.addUsersToCache(getAllUsers());
	}
}

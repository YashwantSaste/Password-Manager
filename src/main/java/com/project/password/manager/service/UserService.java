package com.project.password.manager.service;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.database.DataRepository;
import com.project.password.manager.model.IUser;

public class UserService implements IService {

	@NotNull
	private final DataRepository<IUser, String> userRepository;

	public UserService(@NotNull DataRepository<IUser, String> userRepository) {
		this.userRepository = userRepository;
	}

	@NotNull
	public IUser getUser(@NotNull String id) {
		return userRepository.findById(id);
	}

	public void saveUser(@NotNull IUser user) {
		userRepository.save(user);
	}
}

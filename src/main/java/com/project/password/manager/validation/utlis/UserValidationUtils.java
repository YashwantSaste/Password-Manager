package com.project.password.manager.validation.utlis;

import jakarta.validation.constraints.NotNull;

import com.project.password.manager.database.DataRepository;
import com.project.password.manager.exceptions.EntityNotFoundException;
import com.project.password.manager.guice.GuicePlatform;
import com.project.password.manager.model.IUser;
import com.project.password.manager.service.UserService;

public class UserValidationUtils {

	private static UserService userService = GuicePlatform.getInstance(UserService.class);

	private UserValidationUtils() {
		//
	}

	@NotNull
	public static IUser requireUser(@NotNull String userId) {
		IUser user = userService.getUser(userId);
		return requireResolvedUser(user, userId);
	}

	@NotNull
	public static IUser requireUser(@NotNull DataRepository<IUser, String> userRepository, @NotNull String userId) {
		return requireResolvedUser(userRepository.findById(userId), userId);
	}

	@NotNull
	private static IUser requireResolvedUser(IUser user, @NotNull String userId) {
		if (user != null) {
			return user;
		}
		throw new EntityNotFoundException("User does not exist with id " + userId);
	}

}

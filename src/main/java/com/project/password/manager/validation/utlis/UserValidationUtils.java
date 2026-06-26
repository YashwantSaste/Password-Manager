package com.project.password.manager.validation.utlis;

import org.jetbrains.annotations.NotNull;

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
		if (user == null) {
			throw new EntityNotFoundException("User does not exist with id " + userId);
		}
		return user;
	}

}

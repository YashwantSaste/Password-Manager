package com.project.password.manager.exceptions;

import org.jetbrains.annotations.NotNull;

public class UserNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;

	public UserNotFoundException(@NotNull String message) {
		super(message);
	}

}

package com.project.password.manager.exceptions;

import org.jetbrains.annotations.NotNull;

public class InvalidAccessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidAccessException(@NotNull String message) {
		super(message);
	}

}

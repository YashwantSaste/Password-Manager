package com.project.password.manager.exceptions;

import jakarta.validation.constraints.NotNull;

public class InvalidAppModeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidAppModeException(@NotNull String message) {
		super(message);
	}

	public InvalidAppModeException(@NotNull String message, @NotNull Throwable cause) {
		super(message, cause);
	}
}

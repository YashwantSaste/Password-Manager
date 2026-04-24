package com.project.password.manager.exceptions;

import org.jetbrains.annotations.NotNull;

public class InvalidAppModeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidAppModeException(@NotNull String message) {
		super(message);
	}
}

package com.project.password.manager.exceptions;

import org.jetbrains.annotations.NotNull;

public class UnauthorizedSessionException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UnauthorizedSessionException(@NotNull String message) {
		super(message);
	}
}

package com.project.password.manager.exceptions;

import org.jetbrains.annotations.NotNull;

public class IlleagalAccessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public IlleagalAccessException(@NotNull String message) {
		super(message);
	}

}

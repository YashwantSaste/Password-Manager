package com.project.password.manager.exceptions;

import org.jetbrains.annotations.NotNull;

public class EntityNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EntityNotFoundException(@NotNull String message) {
		super(message);
	}
}

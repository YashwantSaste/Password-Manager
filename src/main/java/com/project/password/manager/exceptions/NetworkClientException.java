package com.project.password.manager.exceptions;

import org.jetbrains.annotations.NotNull;

public class NetworkClientException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NetworkClientException(@NotNull String message) {
		super(message);
	}

	public NetworkClientException(@NotNull String message, @NotNull Throwable cause) {
		super(message, cause);
	}
}

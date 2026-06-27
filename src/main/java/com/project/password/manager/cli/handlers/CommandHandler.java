package com.project.password.manager.cli.handlers;

import jakarta.validation.constraints.NotNull;

public interface CommandHandler<TRequest> {

	void handle(@NotNull TRequest request);
}
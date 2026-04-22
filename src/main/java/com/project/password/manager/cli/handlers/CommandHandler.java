package com.project.password.manager.cli.handlers;

import org.jetbrains.annotations.NotNull;

public interface CommandHandler<TRequest> {

	void handle(@NotNull TRequest request);
}
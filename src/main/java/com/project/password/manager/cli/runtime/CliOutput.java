package com.project.password.manager.cli.runtime;

import org.jetbrains.annotations.NotNull;

public interface CliOutput {

	void info(@NotNull String message);

	void error(@NotNull String message);
}
package com.project.password.manager.cli.runtime;

import jakarta.validation.constraints.NotNull;

public interface CliOutput {

	void info(@NotNull String message);

	void error(@NotNull String message);
}
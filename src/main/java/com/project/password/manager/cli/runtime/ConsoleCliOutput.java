package com.project.password.manager.cli.runtime;

import jakarta.validation.constraints.NotNull;

import com.google.inject.Singleton;

@Singleton
public class ConsoleCliOutput implements CliOutput {

	@Override
	public void info(@NotNull String message) {
		System.out.println(message);
	}

	@Override
	public void error(@NotNull String message) {
		System.err.println(message);
	}
}
package com.project.password.manager.cli.runtime;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.exceptions.UnauthorizedSessionException;

import picocli.CommandLine;
import picocli.CommandLine.ParseResult;

public class CliErrorHandler implements CommandLine.IExecutionExceptionHandler {

	@Override
	public int handleExecutionException(@NotNull Exception ex, @NotNull CommandLine commandLine,
			@NotNull ParseResult parseResult) {
		Throwable error = ex instanceof RuntimeException && ex.getCause() != null ? ex.getCause() : ex;
		String message = error.getMessage();
		if (message == null || message.isBlank()) {
			message = error.getClass().getSimpleName();
		}
		if (error instanceof UnauthorizedSessionException) {
			commandLine.getErr().println("Authorization error: " + message);
		} else {
			commandLine.getErr().println("Error: " + message);
		}
		return commandLine.getCommandSpec().exitCodeOnExecutionException();
	}
}
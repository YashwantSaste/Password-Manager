package com.project.password.manager.cli.commands;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.cli.handlers.VaultDefaultCommandHandler;

import picocli.CommandLine.Command;

@Command(name = "default", mixinStandardHelpOptions = true, description = "Shows the default vault for the authenticated user.")
public class VaultDefaultCommand extends DelegatingCliCommand<VaultDefaultCommand.Request, VaultDefaultCommandHandler> {

	@Override
	@NotNull
	protected Request buildRequest() {
		return new Request();
	}

	public static final class Request {
		// Marker request.
	}
}
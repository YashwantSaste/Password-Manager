package com.project.password.manager.cli.commands;

import org.jetbrains.annotations.NotNull;

import picocli.CommandLine.Command;

@Command(name = "whoami", mixinStandardHelpOptions = true, description = "Prints the currently authenticated user.")
public class WhoAmICommand extends DelegatingCliCommand<WhoAmICommand.Request, com.project.password.manager.cli.handlers.WhoAmICommandHandler> {

	@Override
	@NotNull
	protected Request buildRequest() {
		return new Request();
	}

	public static final class Request {
		// Marker request.
	}
}
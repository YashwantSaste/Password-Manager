package com.project.password.manager.cli.commands;

import org.jetbrains.annotations.NotNull;

import picocli.CommandLine.Command;

@Command(name = "logout", mixinStandardHelpOptions = true, description = "Revokes the active CLI session.")
public class LogoutCommand extends DelegatingCliCommand<LogoutCommand.Request, com.project.password.manager.cli.handlers.LogoutCommandHandler> {

	@Override
	@NotNull
	protected Request buildRequest() {
		return new Request();
	}

	public static final class Request {
		// Marker request.
	}
}
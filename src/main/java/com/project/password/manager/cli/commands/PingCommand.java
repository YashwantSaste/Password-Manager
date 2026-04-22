package com.project.password.manager.cli.commands;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.cli.handlers.PingCommandHandler;

import picocli.CommandLine.Command;

@Command(name = "ping", mixinStandardHelpOptions = true, description = "Verifies the active CLI session and prints a health response.")
public class PingCommand extends DelegatingCliCommand<PingCommand.Request, PingCommandHandler> {

	@Override
	@NotNull
	protected Request buildRequest() {
		return new Request();
	}

	public static final class Request {
		// Marker request.
	}
}
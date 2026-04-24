package com.project.password.manager.cli.commands.auth;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.cli.commands.DelegatingCliCommand;
import com.project.password.manager.cli.handlers.auth.LogoutCommandHandler;

import picocli.CommandLine.Command;

@Command(name = "logout", mixinStandardHelpOptions = true, description = "Revokes the active CLI session.")
public class LogoutCommand extends DelegatingCliCommand<LogoutCommand.Request, LogoutCommandHandler> {

	@Override
	@NotNull
	protected Request buildRequest() {
		return new Request();
	}

	public static final class Request {
		// Marker request.
	}
}
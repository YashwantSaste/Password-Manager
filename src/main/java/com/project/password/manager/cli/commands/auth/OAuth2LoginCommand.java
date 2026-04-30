package com.project.password.manager.cli.commands.auth;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.cli.commands.DelegatingCliCommand;
import com.project.password.manager.cli.handlers.auth.OAuth2LoginCommandHandler;

import picocli.CommandLine.Command;

@Command(name = "login", mixinStandardHelpOptions = true, description = "Starts an OAuth2 device-code login and opens a CLI session.")
public class OAuth2LoginCommand extends DelegatingCliCommand<OAuth2LoginCommand.Request, OAuth2LoginCommandHandler> {

	@Override
	@NotNull
	protected Request buildRequest() {
		return new Request();
	}

	public static final class Request {
		// Marker request.
	}
}
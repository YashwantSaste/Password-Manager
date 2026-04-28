package com.project.password.manager.cli.commands.auth;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.cli.commands.DelegatingCliCommand;
import com.project.password.manager.cli.handlers.auth.OAuth2VerifyCommandHandler;

import picocli.CommandLine.Command;

@Command(name = "verify", mixinStandardHelpOptions = true, description = "Validates the local OAuth2 configuration required for device-code login.")
public class OAuth2VerifyCommand extends DelegatingCliCommand<OAuth2VerifyCommand.Request, OAuth2VerifyCommandHandler> {

	@Override
	@NotNull
	protected Request buildRequest() {
		return new Request();
	}

	public static final class Request {
		// Marker request.
	}
}
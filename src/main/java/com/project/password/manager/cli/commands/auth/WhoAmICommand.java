package com.project.password.manager.cli.commands.auth;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.cli.commands.DelegatingCliCommand;
import com.project.password.manager.cli.handlers.auth.WhoAmICommandHandler;

import picocli.CommandLine.Command;

@Command(name = "whoami", mixinStandardHelpOptions = true, description = "Prints the currently authenticated user.")
public class WhoAmICommand extends DelegatingCliCommand<WhoAmICommand.Request, WhoAmICommandHandler> {

	@Override
	@NotNull
	protected Request buildRequest() {
		return new Request();
	}

	public static final class Request {
		// Marker request.
	}
}
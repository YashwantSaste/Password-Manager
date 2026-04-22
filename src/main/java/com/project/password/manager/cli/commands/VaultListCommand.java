package com.project.password.manager.cli.commands;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.cli.handlers.VaultListCommandHandler;

import picocli.CommandLine.Command;

@Command(name = "list", mixinStandardHelpOptions = true, description = "Lists vaults for the authenticated user.")
public class VaultListCommand extends DelegatingCliCommand<VaultListCommand.Request, VaultListCommandHandler> {

	@Override
	@NotNull
	protected Request buildRequest() {
		return new Request();
	}

	public static final class Request {
		// Marker request.
	}
}
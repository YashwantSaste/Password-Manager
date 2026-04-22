package com.project.password.manager.cli.commands;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.cli.handlers.LoginCommandHandler;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "login", mixinStandardHelpOptions = true, description = "Authenticates a user and opens a CLI session.")
public class LoginCommand extends DelegatingCliCommand<LoginCommand.Request, LoginCommandHandler> {

	@Parameters(index = "0", paramLabel = "username", description = "User identifier")
	private String username;

	@Parameters(index = "1", paramLabel = "password", description = "User password")
	private String password;

	@Override
	@NotNull
	protected Request buildRequest() {
		return new Request(username, password);
	}

	public static final class Request {
		@NotNull
		private final String username;
		@NotNull
		private final String password;

		public Request(@NotNull String username, @NotNull String password) {
			this.username = username;
			this.password = password;
		}

		@NotNull
		public String getUsername() {
			return username;
		}

		@NotNull
		public String getPassword() {
			return password;
		}
	}
}
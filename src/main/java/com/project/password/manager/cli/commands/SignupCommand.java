package com.project.password.manager.cli.commands;

import org.jetbrains.annotations.NotNull;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "signup", mixinStandardHelpOptions = true, description = "Creates a user and opens a CLI session.")
public class SignupCommand extends DelegatingCliCommand<SignupCommand.Request, com.project.password.manager.cli.handlers.SignupCommandHandler> {

	@Parameters(index = "0", paramLabel = "username", description = "New user identifier")
	private String username;

	@Parameters(index = "1", paramLabel = "password", description = "New user password")
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
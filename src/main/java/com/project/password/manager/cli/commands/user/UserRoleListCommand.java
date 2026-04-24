package com.project.password.manager.cli.commands.user;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.cli.commands.DelegatingCliCommand;
import com.project.password.manager.cli.handlers.user.UserRoleListCommandHandler;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "list", mixinStandardHelpOptions = true, description = "List the roles assigned to a user.")
public class UserRoleListCommand extends DelegatingCliCommand<UserRoleListCommand.Request, UserRoleListCommandHandler> {

	@Parameters(index = "0", description = "Username to inspect.")
	private String username;

	@Override
	@NotNull
	protected Request buildRequest() {
		return new Request(username);
	}

	public static final class Request {
		private final String username;

		public Request(@NotNull String username) {
			this.username = username;
		}

		@NotNull
		public String getUsername() {
			return username;
		}
	}
}
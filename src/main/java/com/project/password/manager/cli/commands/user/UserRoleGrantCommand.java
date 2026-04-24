package com.project.password.manager.cli.commands.user;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.cli.commands.DelegatingCliCommand;
import com.project.password.manager.cli.handlers.user.UserRoleGrantCommandHandler;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "grant", mixinStandardHelpOptions = true, description = "Grant a role to a user.")
public class UserRoleGrantCommand extends DelegatingCliCommand<UserRoleGrantCommand.Request, UserRoleGrantCommandHandler> {

	@Parameters(index = "0", description = "Username to update.")
	private String username;

	@Parameters(index = "1", description = "Role to grant.")
	private String role;

	@Override
	@NotNull
	protected Request buildRequest() {
		return new Request(username, role);
	}

	public static final class Request {
		private final String username;
		private final String role;

		public Request(@NotNull String username, @NotNull String role) {
			this.username = username;
			this.role = role;
		}

		@NotNull
		public String getUsername() {
			return username;
		}

		@NotNull
		public String getRole() {
			return role;
		}
	}
}
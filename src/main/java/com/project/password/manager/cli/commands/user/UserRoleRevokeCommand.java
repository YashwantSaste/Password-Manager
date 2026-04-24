package com.project.password.manager.cli.commands.user;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.cli.commands.DelegatingCliCommand;
import com.project.password.manager.cli.handlers.user.UserRoleRevokeCommandHandler;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "revoke", mixinStandardHelpOptions = true, description = "Revoke a role from a user.")
public class UserRoleRevokeCommand extends DelegatingCliCommand<UserRoleRevokeCommand.Request, UserRoleRevokeCommandHandler> {

	@Parameters(index = "0", description = "Username to update.")
	private String username;

	@Parameters(index = "1", description = "Role to revoke.")
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
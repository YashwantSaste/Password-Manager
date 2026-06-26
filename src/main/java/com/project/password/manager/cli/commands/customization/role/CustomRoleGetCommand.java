package com.project.password.manager.cli.commands.customization.role;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.cli.commands.DelegatingCliCommand;
import com.project.password.manager.cli.handlers.customization.role.CustomRoleGetCommandHandler;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "get", mixinStandardHelpOptions = true, description = "Searches custom roles configured for the application.")
public class CustomRoleGetCommand
		extends DelegatingCliCommand<CustomRoleGetCommand.Request, CustomRoleGetCommandHandler> {

	@Option(names = "--role", description = "role id")
	private String roleId;

	public static final class Request {

		private final String roleId;

		public Request(@NotNull String roleId) {
			this.roleId = roleId;
		}

		@NotNull
		public String getRoleId() {
			return roleId;
		}

	}

	@Override
	@NotNull
	protected Request buildRequest() {
		return new Request(roleId);
	}
}

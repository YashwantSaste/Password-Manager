package com.project.password.manager.cli.commands.customization.role;

import jakarta.validation.constraints.NotNull;

import com.project.password.manager.cli.commands.DelegatingCliCommand;
import com.project.password.manager.cli.handlers.customization.role.CustomRoleListCommandHandler;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "list", mixinStandardHelpOptions = true, description = "Searches custom roles configured for the application.")
public class CustomRoleListCommand extends DelegatingCliCommand<CustomRoleListCommand.Request, CustomRoleListCommandHandler> {

	@Option(names = "--roles", split = ",", description = "Comma-separated role names")
	private String[] roleIds;

	public static final class Request {

		private final String[] roleIds;

		public Request(@NotNull String[] roleIds) {
			this.roleIds = roleIds;
		}

		@NotNull
		public String[] getRoleIds() {
			return roleIds;
		}

	}

	@Override
	@NotNull
	protected Request buildRequest() {
		return new Request(roleIds == null ? new String[0] : roleIds);
	}
}

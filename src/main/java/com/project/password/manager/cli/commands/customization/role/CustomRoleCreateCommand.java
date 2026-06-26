package com.project.password.manager.cli.commands.customization.role;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.cli.commands.DelegatingCliCommand;
import com.project.password.manager.cli.handlers.customization.role.CustomRoleCreateCommandHandler;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "create", mixinStandardHelpOptions = true, description = "Creates custom role for the application.")
public class CustomRoleCreateCommand
		extends DelegatingCliCommand<CustomRoleCreateCommand.Request, CustomRoleCreateCommandHandler> {

	@Option(names = "--roleId", description = "Unique role id.")
	private String roleId;

	@Option(names = "--roleName", description = "Corresponding role name.")
	private String roleName;

	@Override
	protected Request buildRequest() {
		return new Request(roleId, roleName);
	}

	public static final class Request {

		@NotNull
		private final String roleId;
		@NotNull
		private final String roleName;

		public Request(@NotNull String roleId, @NotNull String roleName) {
			this.roleId = roleId;
			this.roleName = roleName;
		}

		@NotNull
		public String getRoleId() {
			return roleId;
		}

		@NotNull
		public String getRoleName() {
			return roleName;
		}
	}

}

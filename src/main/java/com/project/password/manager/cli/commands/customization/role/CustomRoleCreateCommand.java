package com.project.password.manager.cli.commands.customization.role;

import jakarta.validation.constraints.NotNull;

import com.project.password.manager.cli.commands.DelegatingCliCommand;
import com.project.password.manager.cli.handlers.customization.role.CustomRoleCreateCommandHandler;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "create", mixinStandardHelpOptions = true, description = "Creates custom role for the application.")
public class CustomRoleCreateCommand
		extends DelegatingCliCommand<CustomRoleCreateCommand.Request, CustomRoleCreateCommandHandler> {

	@Option(names = "--roleId", required = true, description = "Unique role id.")
	private String roleId;

	@Option(names = "--roleName", required = true, description = "Corresponding role name.")
	private String roleName;

	@Option(names = "--read", description = "Allow read operations.")
	private boolean read;

	@Option(names = "--create", description = "Allow create operations.")
	private boolean create;

	@Option(names = "--modify", description = "Allow modify operations.")
	private boolean modify;

	@Option(names = "--delete", description = "Allow delete operations.")
	private boolean delete;

	@Option(names = "--scopeType", description = "Scope type: USER, USER_GROUP, TEAM, VAULT.")
	private String scopeType;

	@Option(names = "--scopeId", description = "Scope id for this role.")
	private String scopeId;

	@Override
	protected Request buildRequest() {
		return new Request(roleId, roleName, read, create, modify, delete, scopeType, scopeId);
	}

	public static final class Request {

		@NotNull
		private final String roleId;
		@NotNull
		private final String roleName;
		private final boolean read;
		private final boolean create;
		private final boolean modify;
		private final boolean delete;
		private final String scopeType;
		private final String scopeId;

		public Request(@NotNull String roleId, @NotNull String roleName, boolean read, boolean create, boolean modify,
				boolean delete, String scopeType, String scopeId) {
			this.roleId = roleId;
			this.roleName = roleName;
			this.read = read;
			this.create = create;
			this.modify = modify;
			this.delete = delete;
			this.scopeType = scopeType;
			this.scopeId = scopeId;
		}

		@NotNull
		public String getRoleId() {
			return roleId;
		}

		@NotNull
		public String getRoleName() {
			return roleName;
		}

		public boolean canRead() {
			return read;
		}

		public boolean canCreate() {
			return create;
		}

		public boolean canModify() {
			return modify;
		}

		public boolean canDelete() {
			return delete;
		}

		public String getScopeType() {
			return scopeType;
		}

		public String getScopeId() {
			return scopeId;
		}
	}

}

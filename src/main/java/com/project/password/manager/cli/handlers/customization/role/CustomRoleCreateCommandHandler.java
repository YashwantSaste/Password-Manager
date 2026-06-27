package com.project.password.manager.cli.handlers.customization.role;

import jakarta.validation.constraints.NotNull;

import com.google.inject.Inject;
import com.project.password.manager.cli.commands.customization.role.CustomRoleCreateCommand;
import com.project.password.manager.cli.commands.customization.role.CustomRoleCreateCommand.Request;
import com.project.password.manager.cli.handlers.AbstractAuthorizedCommandHandler;
import com.project.password.manager.cli.runtime.CliOutput;
import com.project.password.manager.cli.runtime.CliSession;
import com.project.password.manager.model.scope.IScope;
import com.project.password.manager.model.scope.ScopeType;
import com.project.password.manager.model.scope.TeamScope;
import com.project.password.manager.model.scope.UserGroupScope;
import com.project.password.manager.model.scope.UserScope;
import com.project.password.manager.model.scope.VaultScope;
import com.project.password.manager.permission.BasePermission;
import com.project.password.manager.service.CustomRoleService;
import com.project.password.manager.service.UserService;

public class CustomRoleCreateCommandHandler extends AbstractAuthorizedCommandHandler<CustomRoleCreateCommand.Request> {

	@NotNull
	private final CustomRoleService customRoleService;

	@Inject
	public CustomRoleCreateCommandHandler(@NotNull CliSession session, @NotNull UserService userService,
			@NotNull CustomRoleService customRoleService, @NotNull CliOutput output) {
		super(session, userService, output);
		this.customRoleService = customRoleService;
	}

	@Override
	public void handle(@NotNull Request request) {
		customRoleService.createCustomRole(request.getRoleId(), request.getRoleName(),
				new BasePermission(request.canRead(), request.canDelete(), request.canModify(), request.canCreate()),
				createScope(request));
		output.info("Created custom role: " + request.getRoleId());
	}

	@NotNull
	private IScope createScope(@NotNull Request request) {
		String scopeTypeValue = request.getScopeType() == null ? ScopeType.USER.name() : request.getScopeType();
		String scopeId = request.getScopeId() == null ? "" : request.getScopeId();
		ScopeType scopeType = ScopeType.valueOf(scopeTypeValue.trim().toUpperCase());
		return switch (scopeType) {
		case USER -> new UserScope(scopeId);
		case USER_GROUP -> new UserGroupScope(scopeId);
		case TEAM -> new TeamScope(scopeId);
		case VAULT -> new VaultScope(scopeId);
		};
	}
}

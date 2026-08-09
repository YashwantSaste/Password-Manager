package com.project.password.manager.cli.handlers.customization.role;

import com.google.inject.Inject;
import com.project.password.manager.auth.session.ISession;
import com.project.password.manager.cli.commands.customization.role.CustomRoleCreateCommand;
import com.project.password.manager.cli.commands.customization.role.CustomRoleCreateCommand.Request;
import com.project.password.manager.cli.handlers.AbstractAuthorizedCommandHandler;
import com.project.password.manager.cli.runtime.CliOutput;
import com.project.password.manager.cli.runtime.CliSession;
import com.project.password.manager.model.scope.IScope;
import com.project.password.manager.model.scope.ScopeType;
import com.project.password.manager.model.scope.TeamScope;
import com.project.password.manager.model.scope.TeamVaultScope;
import com.project.password.manager.model.scope.UserGroupScope;
import com.project.password.manager.model.scope.UserGroupVaultScope;
import com.project.password.manager.model.scope.UserScope;
import com.project.password.manager.model.scope.VaultScope;
import com.project.password.manager.permission.BasePermission;
import com.project.password.manager.permission.IPermission;
import com.project.password.manager.permission.TeamVaultPermission;
import com.project.password.manager.permission.UserGroupVaultPermission;
import com.project.password.manager.permission.UserScopePermission;
import com.project.password.manager.service.CustomRoleService;
import com.project.password.manager.service.UserService;

import jakarta.validation.constraints.NotNull;

public class CustomRoleCreateCommandHandler extends AbstractAuthorizedCommandHandler<CustomRoleCreateCommand.Request> {

	@NotNull
	private final String currentUserId;
	@NotNull
	private final CustomRoleService customRoleService;

	@Inject
	public CustomRoleCreateCommandHandler(@NotNull CliSession session, @NotNull ISession appSession,
			@NotNull UserService userService, @NotNull CustomRoleService customRoleService, @NotNull CliOutput output) {
		super(session, userService, output);
		this.currentUserId = session.requireUserId();
		this.customRoleService = customRoleService;
	}

	@Override
	public void handle(@NotNull Request request) {
		IScope scope = createScope(request);
		BasePermission basePermission = new BasePermission(request.canRead(), request.canDelete(), request.canModify(),
				request.canCreate());
		IPermission permission = switch (scope.getType()) {
		case TEAM_VAULT -> new TeamVaultPermission(basePermission);
		case USER_GROUP_VAULT -> new UserGroupVaultPermission(basePermission);
		case USER -> new UserScopePermission(basePermission);
		default -> basePermission;
		};
		customRoleService.createCustomRole(request.getRoleId(), request.getRoleName(), permission, scope);
		output.info("Created custom role: " + request.getRoleId());
	}

	@NotNull
	private IScope createScope(@NotNull Request request) {
		String scopeTypeValue = request.getScopeType() == null ? ScopeType.USER.name() : request.getScopeType();
		String scopeId = request.getScopeId() == null ? "" : request.getScopeId();
		ScopeType scopeType = ScopeType.valueOf(scopeTypeValue.trim().toUpperCase());

		if (scopeType == ScopeType.USER && scopeId.isEmpty()) {
			scopeId = currentUserId;
		}

		return switch (scopeType) {
		case USER -> new UserScope(scopeId);
		case USER_GROUP -> new UserGroupScope(scopeId);
		case TEAM -> new TeamScope(scopeId);
		case VAULT -> new VaultScope(scopeId);
		case TEAM_VAULT -> new TeamVaultScope(scopeId);
		case USER_GROUP_VAULT -> new UserGroupVaultScope(scopeId);
		};
	}
}

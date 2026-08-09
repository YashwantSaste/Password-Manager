package com.project.password.manager.permission;

import java.util.Set;

import com.project.password.manager.model.ICustomRole;
import com.project.password.manager.model.UserRole;

import jakarta.validation.constraints.NotNull;

public class UserScopePermission implements IPermission {

	private final IBasePermission basePermission;

	public UserScopePermission(@NotNull IBasePermission basePermission) {
		this.basePermission = basePermission;
	}

	@Override
	@NotNull
	public Set<UserRole> allowedUserRoles() {
		return basePermission.allowedUserRoles();
	}

	@Override
	@NotNull
	public Set<ICustomRole> allowedCustomRoles() {
		return basePermission.allowedCustomRoles();
	}

	@Override
	public IPermissionBuilder buildPermission() {
		return basePermission.buildPermission();
	}

	@Override
	public IBasePermission can() {
		return basePermission.can();
	}
}

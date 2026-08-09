package com.project.password.manager.permission;

import java.util.Set;

import com.project.password.manager.model.ICustomRole;
import com.project.password.manager.model.UserRole;

import jakarta.validation.constraints.NotNull;

public class UserGroupVaultPermission implements IPermission {

	private final IBasePermission basePermission;

	public UserGroupVaultPermission(@NotNull IBasePermission basePermission) {
		this.basePermission = basePermission;
	}

	@Override
	public @NotNull Set<UserRole> allowedUserRoles() {
		return basePermission.allowedUserRoles();
	}

	@Override
	public @NotNull Set<ICustomRole> allowedCustomRoles() {
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

package com.project.password.manager.permission.management;

import java.util.Set;

import jakarta.validation.constraints.NotNull;

import com.project.password.manager.model.ICustomRole;
import com.project.password.manager.model.UserRole;
import com.project.password.manager.permission.IBasePermission;
import com.project.password.manager.permission.IPermission;
import com.project.password.manager.permission.IPermissionBuilder;

public class Permission implements IPermission {

	@Override
	public @NotNull Set<UserRole> allowedUserRoles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPermissionBuilder buildPermission() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IBasePermission can() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @NotNull Set<ICustomRole> allowedCustomRoles() {
		// TODO Auto-generated method stub
		return null;
	}

}

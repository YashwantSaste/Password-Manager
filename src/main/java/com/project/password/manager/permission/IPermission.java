package com.project.password.manager.permission;

import java.util.Set;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.model.UserRole;

public interface IPermission {

	@NotNull
	Set<UserRole> allowedUserRoles();

	IPermissionBuilder buildPermission();

	IBasePermission can();

}

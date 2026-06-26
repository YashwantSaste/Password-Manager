package com.project.password.manager.permission;

import java.util.Set;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.model.ICustomRole;
import com.project.password.manager.model.UserRole;

public interface IPermission {

	@NotNull
	Set<UserRole> allowedUserRoles();

	@NotNull
	Set<ICustomRole> allowedCustomRoles();

	IPermissionBuilder buildPermission();

	IBasePermission can();

}

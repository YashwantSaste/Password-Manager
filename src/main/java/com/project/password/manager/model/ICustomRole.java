package com.project.password.manager.model;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.permission.ICrudPermission;

public interface ICustomRole extends IEntity {

	@NotNull
	String roleName();

	void setId(@NotNull String roleId);

	void setRoleName(@NotNull String roleName);

	@NotNull
	ICrudPermission can();

	void setPermissions(@NotNull ICrudPermission can);

}

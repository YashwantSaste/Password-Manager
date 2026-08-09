package com.project.password.manager.model;

import com.project.password.manager.model.scope.IScope;
import com.project.password.manager.permission.IPermission;

import jakarta.validation.constraints.NotNull;

public interface ICustomRole extends IEntity {

	@NotNull
	String roleName();

	@NotNull
	String getId();

	void setId(@NotNull String roleId);

	void setRoleName(@NotNull String roleName);

	@NotNull
	IScope scope();

	@NotNull
	IPermission permission();

	void setPermission(@NotNull IPermission can);

	void setScope(@NotNull IScope scope);

}

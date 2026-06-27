package com.project.password.manager.model;

import jakarta.validation.constraints.NotNull;

import com.project.password.manager.model.scope.IScope;
import com.project.password.manager.permission.IBasePermission;

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
	IBasePermission can();

	void setPermissions(@NotNull IBasePermission can);
	
	void setScope(@NotNull IScope scope);

}

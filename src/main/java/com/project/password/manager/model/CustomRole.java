package com.project.password.manager.model;

import jakarta.validation.constraints.NotNull;

import com.project.password.manager.model.database.file.storage.IFileStorableEntity;
import com.project.password.manager.model.scope.IScope;
import com.project.password.manager.model.scope.UserScope;
import com.project.password.manager.permission.BasePermission;
import com.project.password.manager.permission.IBasePermission;

public class CustomRole implements IFileStorableEntity, ICustomRole {

	private String id;
	private String roleName;
	private IBasePermission can = new BasePermission();
	private IScope scope = new UserScope("");

	public CustomRole() {
		// Required by Jackson
	}

	public CustomRole(@NotNull String id, @NotNull String roleName) {
		this.id = id;
		this.roleName = roleName;
	}

	@Override
	@NotNull
	public String roleName() {
		return roleName;
	}

	@Override
	public void setRoleName(@NotNull String roleName) {
		this.roleName = roleName;
	}

	@Override
	@NotNull
	public Class<?> entityClassType() {
		return ICustomRole.class;
	}

	@Override
	@NotNull
	public String getId() {
		return id;
	}

	@Override
	@NotNull
	public String getFileName() {
		return id + ".json";
	}

	@Override
	public void setId(@NotNull String roleId) {
		id = roleId;
	}

	@Override
	@NotNull
	public IBasePermission can() {
		if (can == null) {
			can = new BasePermission();
		}
		return can;
	}

	@Override
	public void setPermissions(@NotNull IBasePermission can) {
		this.can = can;
	}

	@Override
	@NotNull
	public IScope scope() {
		if (scope == null) {
			scope = new UserScope("");
		}
		return scope;
	}

	@Override
	public void setScope(@NotNull IScope scope) {
		this.scope=scope;
	}

}

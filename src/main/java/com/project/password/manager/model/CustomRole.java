package com.project.password.manager.model;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.model.database.file.storage.IFileStorableEntity;
import com.project.password.manager.permission.ICrudPermission;

public class CustomRole implements IFileStorableEntity, ICustomRole {

	private String id;
	private String roleName;
	private ICrudPermission can;

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
	public ICrudPermission can() {
		return can;
	}

	@Override
	public void setPermissions(@NotNull ICrudPermission can) {
		this.can = can;
	}

}

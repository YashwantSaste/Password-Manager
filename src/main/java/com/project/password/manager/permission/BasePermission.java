package com.project.password.manager.permission;

import java.util.Set;

import com.project.password.manager.model.ICustomRole;
import com.project.password.manager.model.UserRole;

import jakarta.validation.constraints.NotNull;

public class BasePermission implements IBasePermission {

	private boolean read;
	private boolean delete;
	private boolean modify;
	private boolean create;

	public BasePermission() {
		this(true, false, false, false);
	}

	public BasePermission(@NotNull UserRole role) {
		createPermissionForRole(role);
	}

	public BasePermission(boolean read, boolean delete, boolean modify, boolean create) {
		this.read = read;
		this.delete = delete;
		this.modify = modify;
		this.create = create;
	}

	@Override
	@NotNull
	public Set<UserRole> allowedUserRoles() {
		return Set.of();
	}

	@Override
	public IPermissionBuilder buildPermission() {
		return () -> this;
	}

	@Override
	public IBasePermission can() {
		return this;
	}

	@Override
	public boolean read() {
		return read;
	}

	@Override
	public boolean delete() {
		return delete;
	}

	@Override
	public boolean modify() {
		return modify;
	}

	@Override
	public boolean create() {
		return create;
	}

	@Override
	@NotNull
	public Set<ICustomRole> allowedCustomRoles() {
		return Set.of();
	}

	@Override
	public void setRead(boolean read) {
		this.read = read;
	}

	@Override
	public void setDelete(boolean delete) {
		this.delete = delete;
	}

	@Override
	public void setModify(boolean modify) {
		this.modify = modify;
	}

	@Override
	public void setCreate(boolean create) {
		this.create = create;
	}

	@NotNull
	private BasePermission createPermissionForRole(@NotNull UserRole role) {
		if (role.equals(UserRole.ADMIN)) {
			return (BasePermission) createBasePermissionForAdmin();
		} else {
			return (BasePermission) createBasePermissionForUser();
		}
	}

}

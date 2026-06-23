package com.project.password.manager.permission;

import java.util.Set;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.model.UserRole;

public class BasePermission implements IBasePermission {

	public BasePermission() {
		//
	}

	@Override
	@NotNull
	public Set<UserRole> allowedUserRoles() {
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
	public boolean read() {
		Set<UserRole> roles = allowedUserRoles();

		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean edit() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean create() {
		// TODO Auto-generated method stub
		return false;
	}

}

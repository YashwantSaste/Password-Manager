package com.project.password.manager.user.management;

import java.util.List;

import jakarta.validation.constraints.NotNull;

import com.project.password.manager.model.IUser;

public interface IUserPool {

	@NotNull
	List<IUser> getAdminUsers();

	@NotNull
	List<IUser> getNonAdminUsers();

	@NotNull
	List<IUser> getAllUsers();
}

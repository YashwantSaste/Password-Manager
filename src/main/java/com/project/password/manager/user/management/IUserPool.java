package com.project.password.manager.user.management;

import java.util.List;

import javax.annotation.Nullable;

import com.project.password.manager.model.IUser;

import jakarta.validation.constraints.NotNull;

public interface IUserPool {

	@NotNull
	List<IUser> getAdminUsers();

	@NotNull
	List<IUser> getNonAdminUsers();

	@NotNull
	List<IUser> getAllUsers();

	@Nullable
	IUser getUser(@NotNull String userId);

	@NotNull
	IUser getCurrentLoggedInUser();
}

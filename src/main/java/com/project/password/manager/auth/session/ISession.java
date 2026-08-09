package com.project.password.manager.auth.session;

import com.project.password.manager.model.IUser;

import jakarta.validation.constraints.NotNull;

public interface ISession {

	@NotNull
	ApplicationSession getCurrentSession();

	@NotNull
	IUser getCurrentLoggedInUser();

}

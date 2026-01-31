package com.project.password.manager.model;

import org.jetbrains.annotations.NotNull;

public interface IUser {

	@NotNull
	String getId();

	void setId(@NotNull String setId);
}

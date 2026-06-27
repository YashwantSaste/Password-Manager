package com.project.password.manager.model;

import java.util.List;

import jakarta.validation.constraints.NotNull;

public interface IUserGroup extends HasMetadata {

	@Override
	@NotNull
	default Class<?> entityClassType() {
		return IUserGroup.class;
	}

	@NotNull
	String getId();

	@NotNull
	String getName();

	@NotNull
	List<String> getUsers();

	void setId(@NotNull String id);

	void setName(@NotNull String name);

	void setUsers(@NotNull List<String> users);
}

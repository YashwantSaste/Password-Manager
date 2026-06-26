package com.project.password.manager.model;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.model.database.file.storage.IFileStorableEntity;

public interface ICustomRole extends IFileStorableEntity, IEntity {

	@Override
	@NotNull
	String getId();

	@NotNull
	String roleName();

	void setId(@NotNull String roleId);

	void setRoleName(@NotNull String roleName);

}

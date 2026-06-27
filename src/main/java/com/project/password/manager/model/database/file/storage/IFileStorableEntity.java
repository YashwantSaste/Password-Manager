package com.project.password.manager.model.database.file.storage;

import jakarta.validation.constraints.NotNull;

import com.project.password.manager.model.IEntity;

public interface IFileStorableEntity extends IEntity {

	@NotNull
	String getId();

	@NotNull
	String getFileName();
}

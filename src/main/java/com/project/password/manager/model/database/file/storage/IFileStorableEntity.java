package com.project.password.manager.model.database.file.storage;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.model.IEntity;

public interface IFileStorableEntity extends IEntity {

	@NotNull
	String getId();

	@NotNull
	String getFileName();
}

package com.project.password.manager.model.database.file.storage;

import org.jetbrains.annotations.NotNull;

public interface IFileStorableEntity {

	@NotNull
	String getId();

	@NotNull
	String getFileName();
}

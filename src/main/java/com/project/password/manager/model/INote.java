package com.project.password.manager.model;

import org.jetbrains.annotations.NotNull;

public interface INote {

	@NotNull
	String getId();

	@NotNull
	String getDescription();
}

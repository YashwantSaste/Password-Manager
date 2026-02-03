package com.project.password.manager.model;

import org.jetbrains.annotations.NotNull;

public interface IEntry {

	@NotNull
	String getId();

	@NotNull
	String getTitle();

	@NotNull
	ILogin getLogin();

	@NotNull
	String getTag();

	@NotNull
	String metadata();
}

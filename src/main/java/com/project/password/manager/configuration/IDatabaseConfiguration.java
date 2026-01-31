package com.project.password.manager.configuration;

import org.jetbrains.annotations.NotNull;

public interface IDatabaseConfiguration {

	boolean databaseEnabled();

	@NotNull
	String type();

	@NotNull
	String username();

	@NotNull
	String password();

	@NotNull
	String vendor();

	int port();

	@NotNull
	String host();

	@NotNull
	String databaseName();

	@NotNull
	String ddlMode();
}

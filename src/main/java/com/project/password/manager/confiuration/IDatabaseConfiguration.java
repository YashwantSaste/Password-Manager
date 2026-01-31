package com.project.password.manager.confiuration;

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
	String connectionString();
	
	@NotNull
	String host();
}

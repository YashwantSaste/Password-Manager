package com.project.password.manager.configuration;

import org.jetbrains.annotations.NotNull;

public interface IConfiguration {

	@NotNull
	IDatabaseConfiguration databaseConfiguration();
}

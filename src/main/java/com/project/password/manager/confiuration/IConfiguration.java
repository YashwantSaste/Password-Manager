package com.project.password.manager.confiuration;

import org.jetbrains.annotations.NotNull;

public interface IConfiguration {

	@NotNull
	IDatabaseConfiguration databaseConfiguration();
}

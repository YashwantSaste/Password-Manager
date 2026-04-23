package com.project.password.manager.configuration;

import org.jetbrains.annotations.NotNull;

public interface IAppConfiguration {

	@NotNull
	String name();

	@NotNull
	String version();

}

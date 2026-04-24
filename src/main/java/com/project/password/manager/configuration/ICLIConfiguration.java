package com.project.password.manager.configuration;

import org.jetbrains.annotations.NotNull;

public interface ICLIConfiguration {

	boolean isEnabled();

	@NotNull
	String displayPrompt();

	@NotNull
	String theme();
}

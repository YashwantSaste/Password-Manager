package com.project.password.manager.configuration;

import jakarta.validation.constraints.NotNull;

public interface ICLIConfiguration {

	boolean isEnabled();

	@NotNull
	String displayPrompt();

	@NotNull
	String theme();
}

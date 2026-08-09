package com.project.password.manager.configuration;

import jakarta.validation.constraints.NotNull;

public interface IAuthenticationConfiguration {

	@NotNull
	AuthenticationType type();
}

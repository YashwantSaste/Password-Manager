package com.project.password.manager.model;

import jakarta.validation.constraints.NotNull;

public interface IEntity {
	// marker interface extending all the entities;
	@NotNull
	Class<?> entityClassType();
}

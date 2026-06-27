package com.project.password.manager.model;

import jakarta.validation.constraints.NotNull;

public interface INote {

	@NotNull
	String getId();

	@NotNull
	String getDescription();
}

package com.project.password.manager.model;

import jakarta.validation.constraints.NotNull;

public interface ITag {
	@NotNull
	String getId();

	@NotNull
	String getValue();
}

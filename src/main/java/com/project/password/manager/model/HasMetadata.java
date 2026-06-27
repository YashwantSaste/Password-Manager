package com.project.password.manager.model;

import jakarta.validation.constraints.NotNull;

public interface HasMetadata extends IEntity {

	@NotNull
	IMetadata metadata();

	void setMetadata(@NotNull IMetadata metadata);
}
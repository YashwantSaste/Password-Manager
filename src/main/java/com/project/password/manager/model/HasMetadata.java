package com.project.password.manager.model;

import org.jetbrains.annotations.NotNull;

public interface HasMetadata extends IEntity {

	@NotNull
	IMetadata metadata();

	void setMetadata(@NotNull IMetadata metadata);
}
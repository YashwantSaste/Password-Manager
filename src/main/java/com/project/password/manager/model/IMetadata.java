package com.project.password.manager.model;

import java.time.LocalDateTime;

import org.jetbrains.annotations.NotNull;

public interface IMetadata {

	@NotNull
	LocalDateTime createdAt();

	@NotNull
	LocalDateTime updatedAt();

	@NotNull
	LocalDateTime lastAccessedAt();

	@NotNull
	String version();

	// might be a Enum later but for now kept it as a String
	@NotNull
	String status();
}

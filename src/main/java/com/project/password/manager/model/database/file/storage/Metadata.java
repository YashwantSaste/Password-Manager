package com.project.password.manager.model.database.file.storage;

import java.time.LocalDateTime;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.configuration.application.Configuration;
import com.project.password.manager.model.IMetadata;
import com.project.password.manager.model.Status;

public class Metadata implements IMetadata {

	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private LocalDateTime lastAccessedAt;
	private String version = Configuration.getInstance().appConfiguration().version();
	private Status status;

	public Metadata() {
		// for jackson
	}

	@Override
	@NotNull
	public LocalDateTime createdAt() {
		return createdAt;
	}

	@Override
	@NotNull
	public LocalDateTime updatedAt() {
		return updatedAt;
	}

	@Override
	@NotNull
	public LocalDateTime lastAccessedAt() {
		return lastAccessedAt;
	}

	@Override
	@NotNull
	public String version() {
		return version;
	}

	@Override
	@NotNull
	public Status status() {
		return status;
	}

	@Override
	public void setCreatedAt(@NotNull LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public void setUpdatedAt(@NotNull LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public void setLastAccessedAt(@NotNull LocalDateTime lastAccessedAt) {
		this.lastAccessedAt = lastAccessedAt;
	}

	@Override
	public void setVersion(@NotNull String version) {
		this.version = version;
	}

	@Override
	public void setStatus(@NotNull Status status) {
		this.status = status;
	}
}

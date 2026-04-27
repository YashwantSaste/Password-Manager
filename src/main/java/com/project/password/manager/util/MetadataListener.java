package com.project.password.manager.util;

import java.time.LocalDateTime;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.model.HasMetadata;
import com.project.password.manager.model.IEntity;
import com.project.password.manager.model.IMetadata;
import com.project.password.manager.model.Status;

public final class MetadataListener {

	private static final String DEFAULT_VERSION = "1.0";

	private MetadataListener() {
	}

	public static void beforeCreate(@NotNull IEntity entity) {
		touch(entity, true);
	}

	public static void beforeUpdate(@NotNull IEntity entity) {
		touch(entity, false);
	}

	private static void touch(@NotNull IEntity entity, boolean create) {
		if (!(entity instanceof HasMetadata hasMetadata)) {
			return;
		}
		IMetadata metadata = hasMetadata.metadata();
		if (metadata == null) {
			return;
		}
		LocalDateTime now = LocalDateTime.now();
		if (create && metadata.createdAt() == null) {
			metadata.setCreatedAt(now);
		}
		if (metadata.lastAccessedAt() == null) {
			metadata.setLastAccessedAt(now);
		}
		if (metadata.updatedAt() == null || !create) {
			metadata.setUpdatedAt(now);
		}
		if (metadata.version() == null || metadata.version().isBlank()) {
			metadata.setVersion(DEFAULT_VERSION);
		}
		if (metadata.status() == null) {
			metadata.setStatus(Status.ACTIVE);
		}
		hasMetadata.setMetadata(metadata);
	}
}
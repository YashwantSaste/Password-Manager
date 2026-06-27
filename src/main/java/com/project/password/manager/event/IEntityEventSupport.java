package com.project.password.manager.event;

import jakarta.validation.constraints.NotNull;

import com.project.password.manager.model.IEntity;

public interface IEntityEventSupport {

	@NotNull
	<T extends IEntity> T snapshot(@NotNull T entity);

	void publishCreated(@NotNull IEntity entity);

	void publishUpdated(@NotNull IEntity before, @NotNull IEntity after);

	void publishDeleted(@NotNull IEntity entity);
}
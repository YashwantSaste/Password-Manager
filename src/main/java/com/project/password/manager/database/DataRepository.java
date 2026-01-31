package com.project.password.manager.database;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface DataRepository<T, Id> {

	void save(@NotNull T entity);

	@Nullable
	T findById(@NotNull Id id);

	void delete(@NotNull T entity);

	void update(@NotNull T id);
}

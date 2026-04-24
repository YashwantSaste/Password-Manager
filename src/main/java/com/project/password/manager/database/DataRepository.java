package com.project.password.manager.database;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface DataRepository<T, Id> {

	void save(@NotNull T entity);

	@Nullable
	T findById(@NotNull Id id);

	@NotNull
	List<T> findAll();

	void delete(@NotNull Id id);

	void update(@NotNull Id id, @NotNull T entity);
}

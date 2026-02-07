package com.project.password.manager.database.file.storage;

import java.io.File;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.project.password.manager.database.DataRepository;
import com.project.password.manager.model.IEntity;

public class FileStorageRepository<T extends IEntity, Id> implements DataRepository<IEntity, Id> {

	@NotNull
	private final File workspace;

	public FileStorageRepository(@NotNull File workspace) {
		this.workspace = workspace;
	}

	@Override
	public void save(@NotNull IEntity entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public @Nullable IEntity findById(@NotNull Id id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(@NotNull IEntity entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(@NotNull IEntity id) {
		// TODO Auto-generated method stub

	}

}

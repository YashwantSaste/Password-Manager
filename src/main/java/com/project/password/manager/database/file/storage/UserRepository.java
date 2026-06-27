package com.project.password.manager.database.file.storage;

import java.io.File;

import jakarta.validation.constraints.NotNull;

import com.project.password.manager.logging.ITransactionLogger;
import com.project.password.manager.model.database.file.storage.User;

public class UserRepository extends FileStorageRepository<User, String> {

	private static final String USERS_WORKSPACE_FOLDER = "users";

	public UserRepository(@NotNull File workspace, @NotNull ITransactionLogger transactionLogger) {
		super(new File(workspace, USERS_WORKSPACE_FOLDER), transactionLogger);
	}

	@Override
	protected File resolveEntityDirectoryInFileSystem(String id) {
		return new File(workspace, id);
	}

	@Override
	protected Class<User> getEntityClass() {
		return User.class;
	}

	@Override
	protected String getEntityFileName() {
		return "user.json";
	}
}

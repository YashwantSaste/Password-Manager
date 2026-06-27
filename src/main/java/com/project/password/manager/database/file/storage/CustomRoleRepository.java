package com.project.password.manager.database.file.storage;

import java.io.File;

import jakarta.validation.constraints.NotNull;

import com.project.password.manager.logging.ITransactionLogger;
import com.project.password.manager.model.CustomRole;

public class CustomRoleRepository extends FileStorageRepository<CustomRole, String> {

	private static final String TEAM_WORKSPACE_FOLDER = "roles";

	public CustomRoleRepository(@NotNull File workspace, @NotNull ITransactionLogger transactionLogger) {
		super(new File(workspace, TEAM_WORKSPACE_FOLDER), transactionLogger);
	}

	@Override
	@NotNull
	protected File resolveEntityDirectoryInFileSystem(@NotNull String id) {
		return new File(workspace, id);
	}

	@Override
	@NotNull
	protected Class<CustomRole> getEntityClass() {
		return CustomRole.class;
	}

	@Override
	@NotNull
	protected String getEntityFileName() {
		return "role.json";
	}

}

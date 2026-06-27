package com.project.password.manager.database.file.storage;

import java.io.File;

import jakarta.validation.constraints.NotNull;

import com.project.password.manager.logging.ITransactionLogger;
import com.project.password.manager.model.database.file.storage.Team;

public class TeamRepository extends FileStorageRepository<Team, String> {

	private static final String TEAM_WORKSPACE_FOLDER = "teams";

	public TeamRepository(@NotNull File workspace, @NotNull ITransactionLogger transactionLogger) {
		super(new File(workspace, TEAM_WORKSPACE_FOLDER), transactionLogger);
	}

	@Override
	@NotNull
	protected File resolveEntityDirectoryInFileSystem(@NotNull String id) {
		return new File(workspace, id);
	}

	@Override
	@NotNull
	protected Class<Team> getEntityClass() {
		return Team.class;
	}

	@Override
	@NotNull
	protected String getEntityFileName() {
		return "team.json";
	}

}

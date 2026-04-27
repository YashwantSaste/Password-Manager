package com.project.password.manager.database.file.storage;

import java.io.File;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.model.database.file.storage.Team;

public class TeamRepository extends FileStorageRepository<Team, String> {

	private static final String TEAM_WORKSPACE_FOLDER = "teams";

	public TeamRepository(@NotNull File workspace) {
		super(new File(workspace, TEAM_WORKSPACE_FOLDER));
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

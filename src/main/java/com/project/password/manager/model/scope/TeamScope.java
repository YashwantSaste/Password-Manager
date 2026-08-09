package com.project.password.manager.model.scope;

import com.project.password.manager.guice.GuicePlatform;
import com.project.password.manager.model.IEntity;
import com.project.password.manager.service.TeamService;

import jakarta.validation.constraints.NotNull;

public class TeamScope implements IScope {

	@NotNull
	private String teamId;

	public TeamScope() {
		this.teamId = "";
	}

	public TeamScope(@NotNull String teamId) {
		this.teamId = teamId;
	}

	@Override
	@NotNull
	public ScopeType getType() {
		return ScopeType.TEAM;
	}

	@Override
	@NotNull
	public String getScopeId() {
		return teamId;
	}

	@Override
	@NotNull
	public IEntity getScopedEntity() {
		TeamService teamService = GuicePlatform.getInstance(TeamService.class);
		return teamService.getTeam(teamId);
	}

}

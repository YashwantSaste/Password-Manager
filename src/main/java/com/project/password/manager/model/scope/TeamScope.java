package com.project.password.manager.model.scope;

import jakarta.validation.constraints.NotNull;

public class TeamScope implements IScope {
	
	@NotNull
	private String teamId;
	
	public TeamScope() {
		this.teamId = "";
	}
	
	public TeamScope(@NotNull String teamId) {
		this.teamId=teamId;
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

}

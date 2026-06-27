package com.project.password.manager.model.scope;

import jakarta.validation.constraints.NotNull;

public class UserGroupScope implements IScope {

	@NotNull
	private String groupId;
	
	public UserGroupScope() {
		this.groupId = "";
	}
	
	public UserGroupScope(@NotNull String groupId) {
		this.groupId=groupId;
	}

	@Override
	@NotNull
	public ScopeType getType() {
		return ScopeType.USER_GROUP;
	}

	@Override
	@NotNull
	public String getScopeId() {
		return groupId;
	}
}

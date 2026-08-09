package com.project.password.manager.model.scope;

import com.project.password.manager.guice.GuicePlatform;
import com.project.password.manager.model.IEntity;
import com.project.password.manager.service.UserGroupService;

import jakarta.validation.constraints.NotNull;

public class UserGroupScope implements IScope {

	@NotNull
	private String groupId;

	public UserGroupScope() {
		this.groupId = "";
	}

	public UserGroupScope(@NotNull String groupId) {
		this.groupId = groupId;
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

	@Override
	@NotNull
	public IEntity getScopedEntity() {
		UserGroupService userGroupService = GuicePlatform.getInstance(UserGroupService.class);
		return userGroupService.getGroup(groupId);
	}
}

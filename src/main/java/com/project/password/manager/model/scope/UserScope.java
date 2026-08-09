package com.project.password.manager.model.scope;

import com.project.password.manager.guice.Platform;
import com.project.password.manager.model.IEntity;
import com.project.password.manager.user.management.IUserPool;
import com.project.password.manager.validation.utlis.UserValidationUtils;

import jakarta.validation.constraints.NotNull;

public class UserScope implements IScope {

	@NotNull
	private String userId;

	public UserScope() {
		this.userId = Platform.getPlatformContext().getInstance(IUserPool.class).getCurrentLoggedInUser().getId();
	}

	public UserScope(@NotNull String userId) {
		this.userId = userId;
	}

	@Override
	@NotNull
	public ScopeType getType() {
		return ScopeType.USER;
	}

	@Override
	@NotNull
	public String getScopeId() {
		return userId;
	}

	@Override
	@NotNull
	public IEntity getScopedEntity() {
		return UserValidationUtils.requireUser(userId);
	}

}

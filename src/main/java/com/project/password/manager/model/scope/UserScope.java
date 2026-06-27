package com.project.password.manager.model.scope;

import jakarta.validation.constraints.NotNull;

public class UserScope implements IScope {
	
	@NotNull
	private String userId; 
	
	public UserScope() {
		this.userId = "";
	}
	
	public UserScope(@NotNull String userId) {
		this.userId=userId;
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

}

package com.project.password.manager.model.scope;

import jakarta.validation.constraints.NotNull;

import com.project.password.manager.model.IEntity;
import com.project.password.manager.model.ITeam;
import com.project.password.manager.model.IUser;
import com.project.password.manager.model.IUserGroup;
import com.project.password.manager.model.IVault;

public class ScopeFactory {

	private ScopeFactory() {
		//
	}
	
	@NotNull
	public static IScope createScopeFor(@NotNull IEntity entity) {
		Class<?> entityClass = entity.entityClassType();
		if(entityClass.equals(IUser.class)) {
			IUser user = (IUser) entity;
			return new UserScope(user.getId());
		}
		else if(entityClass.equals(IUserGroup.class)) {
			IUserGroup group = (IUserGroup) entity;
			return new UserGroupScope(group.getId());
		}
		else if(entityClass.equals(ITeam.class)) {
			ITeam team = (ITeam) entity;
			return new TeamScope(team.getId());
		}
		else if(entityClass.equals(IVault.class)) {
			IVault team = (IVault) entity;
			return new VaultScope(team.getId());
		}
		throw new IllegalArgumentException("Cannot create scope for the type: "  + entity.getClass());
	}
}

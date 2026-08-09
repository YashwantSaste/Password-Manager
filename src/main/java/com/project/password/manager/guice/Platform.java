package com.project.password.manager.guice;

import jakarta.validation.constraints.NotNull;

import com.project.password.manager.model.ICustomRole;
import com.project.password.manager.model.IEntity;
import com.project.password.manager.model.IMetadata;
import com.project.password.manager.model.ITeam;
import com.project.password.manager.model.IToken;
import com.project.password.manager.model.IUser;
import com.project.password.manager.model.IUserGroup;
import com.project.password.manager.model.IVault;

public class Platform {

	private Platform() {
		/*
		 * provided non persisted entity instance depending upon the {@code
		 * IDatabaseConfiguration}
		 */
	}

	private static final class HOLDER {
		private static final Platform INSTANCE = new Platform();
	}

	@NotNull
	public static Platform getPlatformContext() {
		return HOLDER.INSTANCE;
	}

	@NotNull
	public IUser getUser() {
		return getEntityInstance(IUser.class);
	}

	@NotNull
	public IVault getVault() {
		return getEntityInstance(IVault.class);
	}

	@NotNull
	public IToken getToken() {
		return getEntityInstance(IToken.class);
	}

	@NotNull
	public IMetadata getMetadata() {
		return getInstance(IMetadata.class);
	}

	@NotNull
	public ITeam getTeam() {
		return getInstance(ITeam.class);
	}

	@NotNull
	public ICustomRole getCustomRole() {
		return getInstance(ICustomRole.class);
	}

	@NotNull
	public IUserGroup getUserGroup() {
		return getInstance(IUserGroup.class);
	}

	@NotNull
	public <T> T getInstance(@NotNull Class<T> clazz) {
		return GuicePlatform.getInstance(clazz);
	}

	@NotNull
	private <T extends IEntity> T getEntityInstance(@NotNull Class<T> clazz) {
		return GuicePlatform.getInstance(clazz);
	}

}

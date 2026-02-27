package com.project.password.manager.guice;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.model.IEntity;
import com.project.password.manager.model.IMetadata;
import com.project.password.manager.model.IUser;
import com.project.password.manager.model.IVault;

public class PlatformEntityProvider {

	private PlatformEntityProvider() {
		/*
		 * provided non persisted entity instance depending upon the {@code
		 * IDatabaseConfiguration}
		 */
	}

	private static final class HOLDER {
		private static final PlatformEntityProvider INSTANCE = new PlatformEntityProvider();
	}

	@NotNull
	public static PlatformEntityProvider getEntityProvider() {
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
	public IMetadata metadata() {
		return GuicePlatform.getInjector().getInstance((IMetadata.class));
	}

	@NotNull
	private <T extends IEntity> T getEntityInstance(@NotNull Class<T> clazz) {
		return GuicePlatform.getInjector().getInstance(clazz);
	}

}

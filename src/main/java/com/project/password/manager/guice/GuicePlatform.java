package com.project.password.manager.guice;

import org.jetbrains.annotations.NotNull;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class GuicePlatform {

	private static class HOLDER {
		private static final Injector INJECTOR = Guice.createInjector(new GuiceModule());
		private static final GuicePlatform INSTANCE = new GuicePlatform();
	}

	@NotNull
	public static GuicePlatform getInstance() {
		return HOLDER.INSTANCE;
	}

	@NotNull
	public static Injector getInjector() {
		return HOLDER.INJECTOR;
	}

	public static void tryInjectMembers(@NotNull Object object) {
		Injector injector = getInjector();
		if (injector != null) {
			injector.injectMembers(object);
		}
	}

}

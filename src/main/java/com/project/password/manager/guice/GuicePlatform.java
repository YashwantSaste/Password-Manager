package com.project.password.manager.guice;

import org.jetbrains.annotations.NotNull;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class GuicePlatform {

	private GuicePlatform() {
	}

	private static class HOLDER {
		private static final Injector INJECTOR = Guice.createInjector(new GuiceModule());
	}

	@NotNull
	private static Injector injector() {
		return HOLDER.INJECTOR;
	}

	@NotNull
	public static Injector getInjector() {
		return injector();
	}

	@NotNull
	public static <T> T getInstance(@NotNull Class<T> type) {
		return injector().getInstance(type);
	}

	public static void injectMembers(@NotNull Object object) {
		injector().injectMembers(object);
	}

}

package com.project.password.manager.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;

import jakarta.validation.constraints.NotNull;

public class GuicePlatform {

	private GuicePlatform() {
	}

	private static class HOLDER {
		private static final Injector INJECTOR = Guice.createInjector(new GuiceModule());
	}

	@NotNull
	public static Injector getInjector() {
		return HOLDER.INJECTOR;
	}

	@NotNull
	public static <T> T getInstance(@NotNull Class<T> type) {
		return getInjector().getInstance(type);
	}

	public static void injectMembers(@NotNull Object object) {
		getInjector().injectMembers(object);
	}

}

package com.project.password.manager.util;

import java.awt.Desktop;
import java.net.URI;

import org.jetbrains.annotations.NotNull;

public final class BrowserUtils {

	private BrowserUtils() {
	}

	public static boolean openUrl(@NotNull String url) {
		if (!ValidationUtils.hasText(url) || !Desktop.isDesktopSupported()) {
			return false;
		}
		Desktop desktop = Desktop.getDesktop();
		if (!desktop.isSupported(Desktop.Action.BROWSE)) {
			return false;
		}
		try {
			desktop.browse(URI.create(url));
			return true;
		} catch (Exception exception) {
			return false;
		}
	}
}
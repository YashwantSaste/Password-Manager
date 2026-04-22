package com.project.password.manager;

import java.io.IOException;

import com.project.password.manager.cli.CLI;
import com.project.password.manager.cli.runtime.CliTheme;
import com.project.password.manager.configuration.application.ApplicationProperties;
import com.project.password.manager.configuration.application.PropertiesReader;
import com.project.password.manager.configuration.application.Workspace;

public class Application {

	public static void main(String[] args) throws IOException {
		Workspace.configureWorkSpace();
		CliTheme.initialize(resolveCliTheme());
		CLI.initCLI(args);
	}

	private static String resolveCliTheme() {
		String environmentTheme = System.getenv("PM_CLI_THEME");
		if (environmentTheme != null && !environmentTheme.isBlank()) {
			return environmentTheme;
		}

		String propertyTheme = PropertiesReader.getInstance()
				.readPropertyAsString(ApplicationProperties.PROPERTY_APP_CLI_THEME);
		return propertyTheme == null || propertyTheme.isBlank() ? "warm-retro" : propertyTheme;
	}
}
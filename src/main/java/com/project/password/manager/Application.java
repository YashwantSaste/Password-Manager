package com.project.password.manager;

import java.io.IOException;

import com.project.password.manager.cli.CLI;
import com.project.password.manager.cli.runtime.CliTheme;
import com.project.password.manager.configuration.application.Workspace;

public class Application {

	public static void main(String[] args) throws IOException {
		Workspace.configureWorkSpace();
		CliTheme.initialize();
		CLI.initCLI(args);
	}
}
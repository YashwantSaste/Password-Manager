package com.project.password.manager;

import java.io.IOException;

import com.project.password.manager.configuration.application.Configuration;
import com.project.password.manager.configuration.application.Workspace;
import com.project.password.manager.util.Logger;

public class Application {

	private static final Logger log = Logger.getLogger(Application.class);

	public static void main(String[] args) throws IOException {
		log.info("App Started");
		Workspace.configureWorkSpace();
		Configuration config = new Configuration();
		System.out.println(config.databaseConfiguration().databaseEnabled());
	}
}

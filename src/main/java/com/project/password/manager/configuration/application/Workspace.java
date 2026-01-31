package com.project.password.manager.configuration.application;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.util.Logger;

public class Workspace {

	private static final Logger log = Logger.getLogger(Workspace.class);

	private static final String USER_DIR_PATH = System.getProperty("user.home");
	private static final String WORKSPACE_DIR = "password-manager";
	private static final File workspace = new File(USER_DIR_PATH, WORKSPACE_DIR);

	private static class HOLDER {
		private static final Workspace instance = new Workspace();
	}

	private Workspace() {
		log.debug("Workspace instance initialized");
	}

	@NotNull
	public static Workspace getInstance() {
		return HOLDER.instance;
	}

	public boolean workspaceExists() {
		boolean exists = workspace.exists();
		log.debug("Workspace exists check at: " + workspace.getAbsolutePath() + " -> " + exists);
		return exists;
	}

	public boolean fileExists(@NotNull File directory) {
		boolean exists = directory.exists();
		log.debug("File exists check at: " + directory.getAbsolutePath() + " -> " + exists);
		return exists;
	}

	public static void configureWorkSpace() {
		log.info("Checking workspace configuration");

		if (!workspace.exists()) {
			log.info("Workspace not found. Creating workspace at: " + workspace.getAbsolutePath());
			if (workspace.mkdir()) {
				log.info("Workspace successfully created");
				try {
					createPropertiesFile();
				} catch (IOException e) {
					log.error("Failed to create application properties file", e);
					throw new RuntimeException("Error while creating application properties file", e);
				}
			} else {
				String errorMsg = "Failed to create workspace directory\n" + "Path       : "
						+ workspace.getAbsolutePath() + "\n" + "OS User    : " + System.getProperty("user.name") + "\n"
						+ "Resolution : Ensure write permissions for user home directory";
				log.error(errorMsg);
				throw new IllegalStateException(errorMsg);
			}
		} else {
			log.info("Workspace already exists at: " + workspace.getAbsolutePath());
		}
	}

	@NotNull
	public File getRoot() {
		log.debug("Returning workspace root: " + workspace.getAbsolutePath());
		return workspace;
	}

	public static void createDirectories(@NotNull String... folders) {
		for (String name : folders) {
			File dir = new File(workspace, name);
			if (!dir.exists()) {
				log.info("Creating directory: " + dir.getAbsolutePath());
				if (!dir.mkdirs()) {
					log.error("Failed to create directory: " + dir.getAbsolutePath());
					throw new IllegalStateException("Failed to create directory: " + dir.getAbsolutePath());
				}
			} else {
				log.debug("Directory already exists: " + dir.getAbsolutePath());
			}
		}
	}

	private static void createPropertiesFile() throws IOException {
		File propertiesFile = new File(workspace, PropertiesReader.PROPERTY_FILE_PATH);
		log.info("Creating application properties file at: " + propertiesFile.getAbsolutePath());
		Properties properties = new Properties();
		properties.setProperty(ApplicationProperties.PROPERTY_APP_VERSION, "1.0.0");
		properties.setProperty(ApplicationProperties.PROPERTY_APP_NAME, "password-manager-cli");
		properties.setProperty(ApplicationProperties.PROPERTY_DATABASE_ENABLED, "false");
		properties.setProperty(ApplicationProperties.PROPERTY_DATABASE_TYPE, "sql");
		properties.setProperty(ApplicationProperties.PROPERTY_DATABASE_USERNAME, "username");
		properties.setProperty(ApplicationProperties.PROPERTY_DATABASE_PASSWORD, "password");
		properties.setProperty(ApplicationProperties.PROPERTY_DATABASE_PORT, "5432");
		properties.setProperty(ApplicationProperties.PROPERTY_DATABASE_VENDOR, "postgres");
		properties.setProperty(ApplicationProperties.PROPERTY_DATABASE_NAME, "name");
		properties.setProperty(ApplicationProperties.PROPERTY_DATABASE_HOST, "host");
		try (FileOutputStream fos = new FileOutputStream(propertiesFile)) {
			properties.store(fos, "Application properties created during workspace initialization");
		}
		log.info("Application properties file created successfully");
	}
}

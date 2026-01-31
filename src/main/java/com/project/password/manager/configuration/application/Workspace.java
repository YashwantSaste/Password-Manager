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
	private static File workspace = new File(USER_DIR_PATH, WORKSPACE_DIR);;

	private static class HOLDER {
		private static Workspace instance = new Workspace();
	}

	private Workspace() {
		//
	}

	@NotNull
	public static Workspace getInstance() {
		return HOLDER.instance;
	}

	public boolean workspaceExists() {
		return workspace.exists();
	}

	public boolean fileExists(@NotNull File directory) {
		return directory.exists();
	}

	public static void configureWorkSpace() {
		if (!workspace.exists()) {
			log.info("Workspace not found. Initializing workspace at: " + workspace.getAbsolutePath());
			if (workspace.mkdir()) {
				log.info("Workspace successfully created at: " + workspace.getAbsolutePath());
				try {
					createPropertiesFile();
				} catch (IOException e) {
					throw new RuntimeException("Error while creating application properties file");
				}
			} else {
				String errorMsg = "Failed to create workspace directory.\n" + "Path       : "
						+ workspace.getAbsolutePath() + "\n" + "Reason     : Insufficient permissions or invalid path\n"
						+ "Resolution : Ensure the application has write access to the user home directory\n"
						+ "OS User    : " + System.getProperty("user.name");
				log.error(errorMsg);
				throw new IllegalStateException(errorMsg);
			}
		} else {
			log.debug("Workspace already exists at: " + workspace.getAbsolutePath());
		}
	}

	@NotNull
	public File getRoot() {
		return workspace;
	}

	public static void createDirectories(@NotNull String... folders) {
		for (String name : folders) {
			File dir = new File(workspace, name);
			if (!dir.exists()) {
				if (!dir.mkdirs()) {
					throw new IllegalStateException("Failed to create directory: " + dir.getAbsolutePath());
				}
			}
		}
	}

	private static void createPropertiesFile() throws IOException {
		Properties properties = new Properties();
		FileOutputStream fileOutputStream = new FileOutputStream(
				new File(workspace, PropertiesReader.PROPERTY_FILE_PATH));
		properties.setProperty(ApplicationProperties.PROPERTY_APP_VERSION, "1.0.0");
		properties.setProperty(ApplicationProperties.PROPERTY_APP_NAME, "password-manager-cli");
		properties.setProperty(ApplicationProperties.PROPERTY_DATABASE_ENABLED, "false");
		properties.setProperty(ApplicationProperties.PROPERTY_DATABASE_TYPE, "sql");
		properties.setProperty(ApplicationProperties.PROPERTY_DATABASE_USERNAME, "username");
		properties.setProperty(ApplicationProperties.PROPERTY_DATABASE_PASSWORD, "password");
		properties.setProperty(ApplicationProperties.PROPERTY_DATABASE_PORT, "5432");
		properties.setProperty(ApplicationProperties.PROPERTY_DATABASE_VENDOR, "postgres");
		properties.setProperty(ApplicationProperties.PROPERTY_DATABASE_CONNECTION_STRING, "connection-string");
		properties.setProperty(ApplicationProperties.PROPERTY_DATABASE_HOST, "host");
		properties.store(fileOutputStream, "Application properties setup while initiating the project");
		fileOutputStream.close();
	}
}

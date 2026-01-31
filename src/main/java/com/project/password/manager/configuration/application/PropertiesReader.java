package com.project.password.manager.configuration.application;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PropertiesReader {
	public static final String PROPERTY_FILE_PATH = "password-manager.properties";
	private static final Map<String, Object> propertiesMap = new ConcurrentHashMap<String, Object>();
	private static final File PROPERTIES_FILE = new File(Workspace.getInstance().getRoot(), PROPERTY_FILE_PATH);

	private PropertiesReader() {
		extractProperties();
	}

	private static class HOLDER {
		private static PropertiesReader instance = new PropertiesReader();
	}

	@NotNull
	public static PropertiesReader getInstance() {
		return HOLDER.instance;
	}

	@Nullable
	public static String getPropertyAsString(@NotNull String key) {
		if (propertiesMap.containsKey(key)) {
			return propertiesMap.get(key).toString();
		}
		return null;
	}

	private void extractProperties() {
		if (propertyFileExist()) {
			try {
				Properties properties = new Properties();
				InputStream propertiesFileStream = new FileInputStream(PROPERTIES_FILE);
				properties.load(propertiesFileStream);
				properties.entrySet().forEach(entry -> propertiesMap.put(entry.getKey().toString(), entry.getValue()));
			} catch (Exception ex) {
				throw new RuntimeException("Error reading the properties file: " + ex.getMessage());
			}
		}
		throw new RuntimeException("Properties file does not exist in the workspace");
	}

	private boolean propertyFileExist() {
		Workspace workspace = Workspace.getInstance();
		if (workspace.workspaceExists() && workspace.fileExists(new File(workspace.getRoot(), PROPERTY_FILE_PATH))) {
			return true;
		}
		return false;
	}
}

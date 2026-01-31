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

	@NotNull
	private File getPropertiesFile() {
		return new File(Workspace.getInstance().getRoot(), PROPERTY_FILE_PATH);
	}

	@Nullable
	public String readPropertyAsString(@NotNull String key) {
		Object value = readPropertiesFromPropertiesMap(key);
		return value != null ? value.toString() : null;
	}

	public int readPropertyAsIntger(@NotNull String key, int defaultValue) {
		String property = readPropertiesFromPropertiesMap(key).toString();
		if (property != null) {
			return Integer.parseInt(readPropertiesFromPropertiesMap(key).toString());
		}
		return defaultValue;
	}

	public boolean readPropertyAsBoolean(@NotNull String key, boolean defaultValue) {
		String property = readPropertiesFromPropertiesMap(key).toString();
		if (property != null) {
			return Boolean.parseBoolean(readPropertiesFromPropertiesMap(key).toString());
		}
		return defaultValue;
	}

	private void extractProperties() {
		if (!propertyFileExist()) {
			throw new RuntimeException("Properties file does not exist in the workspace");
		}
		try (InputStream propertiesFileStream = new FileInputStream(getPropertiesFile())) {
			Properties properties = new Properties();
			properties.load(propertiesFileStream);
			properties.entrySet().forEach(entry -> propertiesMap.put(entry.getKey().toString(), entry.getValue()));
		} catch (Exception ex) {
			throw new RuntimeException("Error reading the properties file", ex);
		}
	}

	private boolean propertyFileExist() {
		Workspace workspace = Workspace.getInstance();
		if (workspace.workspaceExists() && workspace.fileExists(new File(workspace.getRoot(), PROPERTY_FILE_PATH))) {
			return true;
		}
		return false;
	}

	@Nullable
	private static Object readPropertiesFromPropertiesMap(@NotNull String key) {
		if (propertiesMap.containsKey(key)) {
			return propertiesMap.get(key);
		}
		return null;
	}
}

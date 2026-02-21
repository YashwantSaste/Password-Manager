package com.project.password.manager.configuration.application;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.project.password.manager.util.Logger;

public class PropertiesReader {

	private static final Logger log = Logger.getLogger(PropertiesReader.class);

	public static final String PROPERTY_FILE_PATH = "password-manager.properties";
	private static final Map<String, Object> propertiesMap = new ConcurrentHashMap<>();

	private PropertiesReader() {
		log.info("Initializing PropertiesReader");
		extractProperties();
	}

	private static class HOLDER {
		private static PropertiesReader instance = new PropertiesReader();
	}

	@NotNull
	public static PropertiesReader getInstance() {
		log.debug("Returning PropertiesReader singleton instance");
		return HOLDER.instance;
	}

	@NotNull
	private File getPropertiesFile() {
		File file = new File(Workspace.getInstance().getRoot(), PROPERTY_FILE_PATH);
		log.debug("Resolved properties file path: " + file.getAbsolutePath());
		return file;
	}

	@Nullable
	public String readPropertyAsString(@NotNull String key) {
		Object value = readPropertiesFromPropertiesMap(key);
		log.debug("Reading property [String] key=" + key + " value=" + value);
		return value != null ? value.toString() : null;
	}

	public long readPropertyAsLong(@NotNull String key, long defaultValue) {
		Object value = readPropertiesFromPropertiesMap(key);
		if (value != null) {
			log.debug("Reading property [Long] key=" + key + " value=" + value);
			return Long.parseLong(value.toString());
		}
		log.warn("Property not found for key=" + key + ", using default=" + defaultValue);
		return defaultValue;
	}

	public int readPropertyAsIntger(@NotNull String key, int defaultValue) {
		Object value = readPropertiesFromPropertiesMap(key);
		if (value != null) {
			log.debug("Reading property [Integer] key=" + key + " value=" + value);
			return Integer.parseInt(value.toString());
		}
		log.warn("Property not found for key=" + key + ", using default=" + defaultValue);
		return defaultValue;
	}

	public boolean readPropertyAsBoolean(@NotNull String key, boolean defaultValue) {
		Object value = readPropertiesFromPropertiesMap(key);
		if (value != null) {
			log.debug("Reading property [Boolean] key=" + key + " value=" + value);
			return Boolean.parseBoolean(value.toString());
		}
		log.warn("Property not found for key=" + key + ", using default=" + defaultValue);
		return defaultValue;
	}

	private void extractProperties() {
		log.info("Extracting application properties");
		if (!propertyFileExist()) {
			String msg = "Properties file does not exist in workspace: " + getPropertiesFile().getAbsolutePath();
			log.error(msg);
			throw new RuntimeException(msg);
		}

		try (InputStream propertiesFileStream = new FileInputStream(getPropertiesFile())) {
			Properties properties = new Properties();
			properties.load(propertiesFileStream);
			properties.entrySet().forEach(entry -> {
				propertiesMap.put(entry.getKey().toString(), entry.getValue());
				log.debug("Loaded property: " + entry.getKey() + "=" + entry.getValue());
			});

			log.info("Successfully loaded " + propertiesMap.size() + " properties");
		} catch (Exception ex) {
			log.error("Error reading the properties file", ex);
			throw new RuntimeException("Error reading the properties file", ex);
		}
	}

	private boolean propertyFileExist() {
		Workspace workspace = Workspace.getInstance();
		boolean exists = workspace.workspaceExists()
				&& workspace.fileExists(new File(workspace.getRoot(), PROPERTY_FILE_PATH));
		log.debug("Properties file existence check -> " + exists);
		return exists;
	}

	@Nullable
	private static Object readPropertiesFromPropertiesMap(@NotNull String key) {
		if (propertiesMap.containsKey(key)) {
			return propertiesMap.get(key);
		}
		return null;
	}
}

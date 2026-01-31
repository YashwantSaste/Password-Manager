package com.project.password.manager.configuration.application;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.confiuration.IConfiguration;
import com.project.password.manager.confiuration.IDatabaseConfiguration;

public class Configuration implements IConfiguration {

	@NotNull
	private final PropertiesReader propertiesReader = PropertiesReader.getInstance();

	public Configuration() {
		//
	}

	@Override
	@NotNull
	public IDatabaseConfiguration databaseConfiguration() {
		return new DatabaseConfiguration(propertiesReader);
	}

}

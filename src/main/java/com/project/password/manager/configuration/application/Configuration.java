package com.project.password.manager.configuration.application;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.configuration.IArgon2Configuration;
import com.project.password.manager.configuration.IConfiguration;
import com.project.password.manager.configuration.IDatabaseConfiguration;

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

	@Override
	@NotNull
	public IArgon2Configuration argon2Configuration() {
		return new Argon2Configuration(propertiesReader);
	}

}

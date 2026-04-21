package com.project.password.manager.configuration.application;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.configuration.IAESConfiguration;
import com.project.password.manager.configuration.IArgon2Configuration;
import com.project.password.manager.configuration.IConfiguration;
import com.project.password.manager.configuration.IDatabaseConfiguration;
import com.project.password.manager.configuration.IJwtConfiguration;
import com.project.password.manager.configuration.ISaltKeyConfiguration;

public class Configuration implements IConfiguration {

	@NotNull
	private final PropertiesReader propertiesReader = PropertiesReader.getInstance();

	@NotNull
	private static Configuration configuration;

	public static Configuration getInstance()
	{
		if (configuration == null) {
			return configuration;
		}
		return new Configuration();
	}
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

	@Override
	@NotNull
	public IJwtConfiguration jwtConfiguration() {
		return new JwtConfiguration(propertiesReader);
	}

	@Override
	@NotNull
	public  IAESConfiguration aesConfiguration() {
		return new AESConfiguration(propertiesReader);
	}

	@Override
	@NotNull
	public  ISaltKeyConfiguration saltKeyConfiguration() {
		return new SaltKeyConfigurations(propertiesReader);
	}

}

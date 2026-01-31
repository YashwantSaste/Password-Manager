package com.project.password.manager.configuration.application;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.confiuration.IDatabaseConfiguration;

public class DatabaseConfiguration implements IDatabaseConfiguration {

	@NotNull
	private final PropertiesReader reader;

	public DatabaseConfiguration(@NotNull PropertiesReader reader) {
		this.reader = reader;
	}

	@Override
	public boolean databaseEnabled() {
		return reader.readPropertyAsBoolean(ApplicationProperties.PROPERTY_DATABASE_ENABLED, false);
	}

	@Override
	@NotNull
	public String type() {
		return reader.readPropertyAsString(ApplicationProperties.PROPERTY_DATABASE_TYPE);
	}

	@Override
	@NotNull
	public String username() {
		return reader.readPropertyAsString(ApplicationProperties.PROPERTY_DATABASE_USERNAME);
	}

	@Override
	@NotNull
	public String password() {
		return reader.readPropertyAsString(ApplicationProperties.PROPERTY_DATABASE_PASSWORD);
	}

	@Override
	@NotNull
	public String vendor() {
		return reader.readPropertyAsString(ApplicationProperties.PROPERTY_DATABASE_VENDOR);
	}

	@Override
	public int port() {
		return reader.readPropertyAsIntger(ApplicationProperties.PROPERTY_DATABASE_PORT, 5432);
	}

	@Override
	@NotNull
	public String connectionString() {
		return reader.readPropertyAsString(ApplicationProperties.PROPERTY_DATABASE_CONNECTION_STRING);
	}
	
	@Override
	@NotNull
	public String host() {
		return reader.readPropertyAsString(ApplicationProperties.PROPERTY_DATABASE_HOST);
	}

}

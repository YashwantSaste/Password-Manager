package com.project.password.manager.configuration.application;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.configuration.ISaltKeyConfiguration;

public class SaltKeyConfigurations implements ISaltKeyConfiguration{

	@NotNull
	private final PropertiesReader reader;

	public SaltKeyConfigurations(@NotNull PropertiesReader reader) {
		this.reader=reader;
	}
	@Override
	public int iterations() {
		return reader.readPropertyAsIntger(ApplicationProperties.PROPERTY_SALT_KEY_ITERATIONS, 65536);
	}

	@Override
	public int keyLength() {
		return reader.readPropertyAsIntger(ApplicationProperties.PROPERTY_SALT_KEY_LENGTH, 256);
	}

	@Override
	public int saltLength() {
		return reader.readPropertyAsIntger(ApplicationProperties.PROPERTY_SALT_KEY_LENGTH, 16);
	}

}

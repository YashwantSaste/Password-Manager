package com.project.password.manager.configuration.application;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.configuration.IAESConfiguration;

public class AESConfiguration implements IAESConfiguration{

	@NotNull 
	private final PropertiesReader reader;

	public AESConfiguration(@NotNull PropertiesReader reader) {
		this.reader=reader;
	}
	@Override
	@NotNull
	public String transformation() {
		return reader.readPropertyAsString(ApplicationProperties.PROPERTY_AES_TRANSFORMATION);
	}

	@Override
	public int keyLength() {
		return reader.readPropertyAsIntger(ApplicationProperties.PROPERTY_AES_KEY_SIZE, 128);
	}

	@Override
	public int tagLength() {
		return reader.readPropertyAsIntger(ApplicationProperties.PROPERTY_AES_TAG_LENGTH, 128);
	}

	@Override
	public int ivLength() {
		return reader.readPropertyAsIntger(ApplicationProperties.PROPERTY_AES_IV_LENGTH, 12);
	}

}

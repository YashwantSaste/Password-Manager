package com.project.password.manager.model;

import org.jetbrains.annotations.NotNull;

public interface IVault extends IEntity {
	@NotNull
	String getId();

	@NotNull
	String getUserId();

	@NotNull
	String getEncryptedBlob();

	@Override
	@NotNull
	IMetadata metadata();
}

package com.project.password.manager.model;

import org.jetbrains.annotations.NotNull;

public interface IVault {
	@NotNull
	String getId();

	@NotNull
	String getUserId();

	@NotNull
	String getEncryptedBlob();

	@NotNull
	String metadata();
}

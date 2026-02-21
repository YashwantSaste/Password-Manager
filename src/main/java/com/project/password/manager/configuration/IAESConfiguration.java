package com.project.password.manager.configuration;

import org.jetbrains.annotations.NotNull;

public interface IAESConfiguration {

	/** AES transformation: AES/GCM/NoPadding, AES/CBC/PKCS5Padding, etc */
	@NotNull
	String transformation();

	/** Key length in bits (128, 192, 256) */
	int keyLength();

	/** Authentication tag length in bits (for GCM) */
	int tagLength();

	/** IV length in bytes */
	int ivLength();

}
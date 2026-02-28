package com.project.password.manager.encryption;

import org.jetbrains.annotations.NotNull;

public interface IEncryptionService {

	@NotNull
	String encrypt(@NotNull String plainText, @NotNull String userId, @NotNull String password) throws Exception;

	@NotNull
	String decrypt(@NotNull String encryptedText, @NotNull String userId, @NotNull String password) throws Exception;
}

package com.project.password.manager.encryption;

import jakarta.validation.constraints.NotNull;

import com.project.password.manager.model.IVault;

public interface IEncryptionService {

	@NotNull
	String encrypt(@NotNull String plainText, @NotNull IVault vault) throws Exception;

	@NotNull
	String decrypt(@NotNull String encryptedText, @NotNull IVault vault) throws Exception;
}

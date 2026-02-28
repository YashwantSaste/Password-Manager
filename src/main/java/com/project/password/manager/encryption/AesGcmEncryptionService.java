package com.project.password.manager.encryption;

import java.util.Base64;

import javax.crypto.SecretKey;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.model.IUser;
import com.project.password.manager.service.UserService;
import com.project.password.manager.util.AesGcmUtils;
import com.project.password.manager.util.KeyGenerator;

public class AesGcmEncryptionService implements IEncryptionService {

	private final UserService userService;

	public AesGcmEncryptionService(UserService userService) {
		this.userService = userService;
	}

	@Override
	@NotNull
	public String encrypt(@NotNull String plainText, @NotNull String userId, @NotNull String password) throws Exception {
		IUser user = userService.getUser(userId);
		SecretKey key = deriveKeyForUser(user, password);
		return AesGcmUtils.encrypt(key, plainText);
	}

	@Override
	@NotNull
	public String decrypt(@NotNull String encryptedText, @NotNull String userId, @NotNull String password) throws Exception {
		IUser user = userService.getUser(userId);
		SecretKey key = deriveKeyForUser(user, password);
		return AesGcmUtils.decrypt(key, encryptedText);
	}

	/**
	 * Securely derives an encryption key for the user using their password and stored salt.
	 * The key is derived fresh each time to avoid storing encryption keys.
	 * 
	 * @param user The user entity containing the salt
	 * @param password The user's password for key derivation
	 * @return AES encryption key
	 */
	@NotNull
	private SecretKey deriveKeyForUser(@NotNull IUser user, @NotNull String password) {
		if (user.getKeySalt() == null || user.getKeySalt().isEmpty()) {
			throw new IllegalArgumentException("User does not have a valid key salt");
		}
		byte[] salt = Base64.getDecoder().decode(user.getKeySalt());
		return KeyGenerator.generateKeyFromPassword(password, salt);
	}
}

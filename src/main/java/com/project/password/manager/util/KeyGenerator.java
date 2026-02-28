package com.project.password.manager.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.configuration.IAESConfiguration;
import com.project.password.manager.configuration.ISaltKeyConfiguration;
import com.project.password.manager.configuration.application.Configuration;

public class KeyGenerator {

	static ISaltKeyConfiguration saltKeyConfiguration = new Configuration().saltKeyConfiguration();
	static IAESConfiguration aesConfiguration = new Configuration().aesConfiguration();
	static SecureRandom random = new SecureRandom();

	private KeyGenerator() {
		//Derive keys for the encryption and decryption of data
	}

	/**
	 * Derives an AES encryption key from a password and salt using PBKDF2.
	 * This method should be used for encrypting user data.
	 * 
	 * @param password The user's password
	 * @param salt The user's unique salt (should never change)
	 * @return AES encryption key
	 */
	@NotNull
	public static SecretKey generateKeyFromPassword(@NotNull String password, @NotNull byte[] salt) {
		try {
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
			KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, saltKeyConfiguration.iterations(), saltKeyConfiguration.keyLength());
			SecretKey secret = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
			
			// Clear the password from memory
			char[] passwordChars = password.toCharArray();
			java.util.Arrays.fill(passwordChars, '\0');
			
			return secret;
		} catch(NoSuchAlgorithmException | InvalidKeySpecException ex) {
			throw new RuntimeException("Error while generating secret key for the user: "+ ex);
		}
	}

	/**
	 * Generates a cryptographically secure salt for PBKDF2 key derivation.
	 * Each user should have a unique salt that is stored securely.
	 * 
	 * @return 128-bit random salt
	 */
	@NotNull
	public static byte[] generateUserSalt() {
		byte[] salt = new byte[16]; // 128 bits for PBKDF2 salt
		random.nextBytes(salt);
		return salt;
	}

	/**
	 * Generates a cryptographically secure Initialization Vector for AES-GCM.
	 * Each encryption operation should use a unique IV.
	 * 
	 * @return Random IV of configured length
	 */
	@NotNull
	public static byte[] generateIv() {
		byte[] iv = new byte[aesConfiguration.ivLength()];
		random.nextBytes(iv);
		return iv;
	}
}

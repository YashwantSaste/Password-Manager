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

import com.project.password.manager.configuration.ISaltKeyConfiguration;
import com.project.password.manager.configuration.application.Configuration;

public class KeyGenerator {

	static ISaltKeyConfiguration saltKeyConfiguration = new Configuration().saltKeyConfiguration();
	static SecureRandom random =  new SecureRandom();

	private KeyGenerator() {
		//Derive keys for the encryption and decryption of data
	}

	@NotNull
	public static SecretKey generateKeyFromPassword(@NotNull String password) {
		try {
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
			KeySpec spec = new PBEKeySpec(password.toCharArray(),getSaltForKeyGeneration(),saltKeyConfiguration.iterations(),saltKeyConfiguration.keyLength());
			SecretKey secret = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
			return secret;
		} catch(NoSuchAlgorithmException | InvalidKeySpecException ex) {
			throw new RuntimeException("Error while generating secret key for the user: "+ ex);
		}
	}	

	@NotNull
	private static byte[] getSaltForKeyGeneration() {
		byte[] salt = new byte[16];
		random.nextBytes(salt);
		return salt;
	}
}

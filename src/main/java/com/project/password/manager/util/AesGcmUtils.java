package com.project.password.manager.util;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.configuration.IAESConfiguration;
import com.project.password.manager.configuration.application.Configuration;

public class AesGcmUtils {
	static IAESConfiguration aesConfiguration = Configuration.getInstance().aesConfiguration();

	public static byte[] generateIv() {
		return KeyGenerator.generateIv();
	}

	@NotNull
	public static String encrypt(@NotNull SecretKey key, @NotNull String plaintext) throws Exception {
		byte[] iv = generateIv();
		Cipher cipher = Cipher.getInstance(aesConfiguration.transformation());
		GCMParameterSpec spec = new GCMParameterSpec(aesConfiguration.tagLength(), iv);
		cipher.init(Cipher.ENCRYPT_MODE, key, spec);
		byte[] cipherBytes = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
		byte[] ivAndCipher = ByteBuffer.allocate(iv.length + cipherBytes.length).put(iv).put(cipherBytes).array();
		return Base64.getEncoder().encodeToString(ivAndCipher);
	}

	public static String decrypt(@NotNull SecretKey key, @NotNull String base64IvAndCipher) throws Exception {
		byte[] ivAndCipher = Base64.getDecoder().decode(base64IvAndCipher);
		ByteBuffer buffer = ByteBuffer.wrap(ivAndCipher);
		byte[] iv = new byte[aesConfiguration.ivLength()];
		buffer.get(iv);
		byte[] cipherBytes = new byte[buffer.remaining()];
		buffer.get(cipherBytes);
		Cipher cipher = Cipher.getInstance(aesConfiguration.transformation());
		GCMParameterSpec spec = new GCMParameterSpec(aesConfiguration.tagLength(), iv);
		cipher.init(Cipher.DECRYPT_MODE, key, spec);
		byte[] plainBytes = cipher.doFinal(cipherBytes);
		return new String(plainBytes, StandardCharsets.UTF_8);
	}
}

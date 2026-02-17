package com.project.password.manager.service;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.argon.Argon2Encoder;
import com.project.password.manager.model.IUser;

public class AuthService {

	@NotNull
	private final TokenService tokenService;
	@NotNull
	private final UserService userService;
	@NotNull
	private final Argon2Encoder encoder;

	public AuthService(@NotNull TokenService tokenService,@NotNull UserService userService,@NotNull Argon2Encoder encoder) {
		this.tokenService=tokenService;
		this.userService=userService;
		this.encoder=encoder;
	}

	public void login(@NotNull String username,@NotNull String password) {
		IUser user = userService.getUser(username);
		if(user==null) {
			throw new RuntimeException("Given user does not exist");
		}
		if(!encoder.verify(user.getAuthVerifier(),password)) {
			throw new RuntimeException("Password mismatch. Kindly check your password");
		}
		String token = tokenService.createToken(user);
		tokenService.saveToken(user,token);
	}

	public void signup(@NotNull String username,@NotNull String password) {
		IUser user = userService.getUser(username);
		if(user!=null) {
			throw new RuntimeException("User with the username already exists");
		}
		String authHash= encoder.getHashValue(password);
		String keySalt = SecureRandomSaltGenerator.generate(); // 16–32 bytes base64
		byte[] vaultKey = encoder.deriveKey(password, keySalt);
		//to derive vault key byte[] vaultKey = encoder.deriveKey(password, user.getKeySalt());

	}
}

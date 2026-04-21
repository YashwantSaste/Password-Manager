package com.project.password.manager.service;

import java.util.ArrayList;
import java.util.Base64;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.argon.Argon2Encoder;
import com.project.password.manager.guice.PlatformEntityProvider;
import com.project.password.manager.model.IToken;
import com.project.password.manager.model.IUser;
import com.project.password.manager.model.IVault;
import com.project.password.manager.util.KeyGenerator;

public class AuthService {

	@NotNull
	private final TokenService tokenService;
	@NotNull
	private final UserService userService;
	@NotNull
	private final VaultService vaultService;
	@NotNull
	private final Argon2Encoder encoder;

	public AuthService(@NotNull TokenService tokenService, @NotNull UserService userService,
			@NotNull Argon2Encoder encoder, @NotNull VaultService vaultService) {
		this.tokenService=tokenService;
		this.userService=userService;
		this.encoder=encoder;
		this.vaultService = vaultService;
	}

	public void login(@NotNull String username,@NotNull String password) {
		IUser user = userService.getUser(username);
		if(user==null) {
			throw new RuntimeException("Given user does not exist");
		}
		if(!encoder.verify(user.getAuthVerifier(),password)) {
			throw new RuntimeException("Password mismatch. Kindly check your password");
		}
		IToken tokenEntity = PlatformEntityProvider.getEntityProvider().getToken();
		tokenEntity.setUserId(user.getId());
		tokenEntity.setToken(tokenService.createToken(user));
		tokenService.saveToken(user,tokenEntity);
	}

	public void signup(@NotNull String username, @NotNull String password) {
		if (userService.getUser(username) != null) {
			throw new RuntimeException("User already exists");
		}
		IUser newUser = PlatformEntityProvider.getEntityProvider().getUser();
		newUser.setId(username);
		newUser.setName(username);
		newUser.setAuthVerifier(encoder.getHashValue(password));
		byte[] saltBytes = KeyGenerator.generateKeyFromPassword(password).getEncoded();
		String keySalt = Base64.getEncoder().encodeToString(saltBytes);
		newUser.setKeySalt(keySalt);
		newUser.setVaults(new ArrayList<>());
		IVault defaultVault = vaultService.createDefaultVault(newUser);
		newUser.setDefaultVaultId(defaultVault.getId());
		userService.saveUser(newUser);
		IToken tokenEntity = PlatformEntityProvider.getEntityProvider().getToken();
		tokenEntity.setUserId(newUser.getId());
		tokenEntity.setToken(tokenService.createToken(newUser));
		tokenService.saveToken(newUser, tokenEntity);
	}
}

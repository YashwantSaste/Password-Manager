package com.project.password.manager.encryption;

import javax.crypto.SecretKey;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.model.ITeam;
import com.project.password.manager.model.IUser;
import com.project.password.manager.model.IVault;
import com.project.password.manager.model.VaultScope;
import com.project.password.manager.service.UserService;
import com.project.password.manager.service.TeamService;
import com.project.password.manager.util.AesGcmUtils;
import com.project.password.manager.util.KeyGenerator;

public class AesGcmEncryptionService implements IEncryptionService {

	private final UserService userService;
	private final TeamService teamService;

	public AesGcmEncryptionService(UserService userService, TeamService teamService) {
		this.userService = userService;
		this.teamService = teamService;
	}

	@Override
	@NotNull
	public String encrypt(@NotNull String plainText, @NotNull IVault vault) throws Exception {
		SecretKey key = deriveKeyForVault(vault);
		return AesGcmUtils.encrypt(key, plainText);
	}

	@Override
	@NotNull
	public String decrypt(@NotNull String encryptedText, @NotNull IVault vault) throws Exception {
		SecretKey key = deriveKeyForVault(vault);
		return AesGcmUtils.decrypt(key, encryptedText);
	}

	@NotNull
	private SecretKey deriveKeyForVault(@NotNull IVault vault) {
		if (vault.getScope() == VaultScope.USER) {
			IUser user = userService.getUser(vault.getScopeId());
			return KeyGenerator.getSecretKeyFromUserKeySalt(user.getKeySalt());
		}
		if (vault.getScope() == VaultScope.TEAM) {
			ITeam team = teamService.getTeam(vault.getScopeId());
			return KeyGenerator.getSecretKeyFromUserKeySalt(team.getKeySalt());
		}
		throw new IllegalArgumentException("Unsupported vault scope: " + vault.getScope());
	}
}

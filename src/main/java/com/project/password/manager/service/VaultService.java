package com.project.password.manager.service;

import java.util.UUID;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.database.DataRepository;
import com.project.password.manager.model.IUser;
import com.project.password.manager.model.IVault;
import com.project.password.manager.model.database.file.storage.Vault;

public class VaultService {
	@NotNull
	private final DataRepository<IVault, String> vaultRepository;
	@NotNull
	private final DataRepository<IUser, String> userRepository;

	public VaultService(@NotNull DataRepository<IUser, String> userRepository,
			@NotNull DataRepository<IVault, String> vaultRepository) {
		this.userRepository = userRepository;
		this.vaultRepository = vaultRepository;
	}

	public IVault createDefaultVault(@NotNull IUser user) {
		String defaultVaultId = UUID.randomUUID().toString();
		IVault defaultVault = new Vault(defaultVaultId, user.getId(), null);
		vaultRepository.save(defaultVault);
		return defaultVault;
	}
}

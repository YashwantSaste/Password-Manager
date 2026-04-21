package com.project.password.manager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.database.DataRepository;
import com.project.password.manager.exceptions.EntityNotFoundException;
import com.project.password.manager.exceptions.UserNotFoundException;
import com.project.password.manager.guice.PlatformEntityProvider;
import com.project.password.manager.model.IUser;
import com.project.password.manager.model.IVault;

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

	@NotNull
	public IVault createDefaultVault(@NotNull IUser user) {
		List<IVault> vaults = ensureVaultsInitialized(user);
		if (!vaults.isEmpty() || hasDefaultVault(user)) {
			throw new UnsupportedOperationException("A user can have only one default vault.");
		}
		IVault vault = createVault(user, vaults);
		user.setDefaultVaultId(vault.getId());
		persistUserIfPresent(user);
		return vault;
	}

	@NotNull
	public List<IVault> getAllVaults(@NotNull String userId) {
		return ensureVaultsInitialized(getUser(userId));
	}

	@NotNull
	public String createVaultForUser(@NotNull String userId) {
		IUser user = getUser(userId);
		IVault vault = createVault(user, ensureVaultsInitialized(user));
		if (!hasDefaultVault(user)) {
			user.setDefaultVaultId(vault.getId());
		}
		userRepository.update(userId, user);
		return vault.getId();
	}

	@NotNull
	public IVault getDefaultVault(@NotNull String userId) {
		IUser user = getUser(userId);
		String defaultVaultId = user.getDefaultVaultId();
		if (defaultVaultId == null || defaultVaultId.trim().isEmpty()) {
			throw new EntityNotFoundException("The user with id " + userId + " does not have a default vault");
		}
		IVault vault = vaultRepository.findById(defaultVaultId);
		if (vault == null) {
			throw new EntityNotFoundException("The default vault with id " + defaultVaultId + " does not exist");
		}
		return vault;
	}

	@NotNull
	private IVault createVault(@NotNull IUser user) {
		return createVault(user, ensureVaultsInitialized(user));
	}

	@NotNull
	private IVault createVault(@NotNull IUser user, @NotNull List<IVault> vaults) {
		String vaultId = UUID.randomUUID().toString();
		IVault vault = PlatformEntityProvider.getEntityProvider().getVault();
		vault.setId(vaultId);
		vault.setUserId(user.getId());
		vault.setEncryptedBlob("");
		vaultRepository.save(vault);
		vaults.add(vault);
		return vault;
	}

	@NotNull
	private List<IVault> ensureVaultsInitialized(@NotNull IUser user) {
		List<IVault> vaults = user.getVaults();
		if (vaults == null) {
			vaults = new ArrayList<>();
			user.setVaults(vaults);
		}
		return vaults;
	}

	private boolean hasDefaultVault(@NotNull IUser user) {
		String defaultVaultId = user.getDefaultVaultId();
		return defaultVaultId != null && !defaultVaultId.trim().isEmpty();
	}

	private void persistUserIfPresent(@NotNull IUser user) {
		if (userRepository.findById(user.getId()) != null) {
			userRepository.update(user.getId(), user);
		}
	}

	@NotNull
	private IUser getUser(@NotNull String userId) {
		IUser user = userRepository.findById(userId);
		if (user == null) {
			throw new UserNotFoundException("The user with id " + userId + " does not exist");
		}
		return user;
	}
}

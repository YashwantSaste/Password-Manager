package com.project.password.manager.service;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.database.DataRepository;
import com.project.password.manager.exceptions.EntityNotFoundException;
import com.project.password.manager.exceptions.IlleagalAccessException;
import com.project.password.manager.model.ITeam;
import com.project.password.manager.model.IVault;
import com.project.password.manager.model.VaultScope;

public class VaultAccessService {

	@NotNull
	private final DataRepository<IVault, String> vaultRepository;
	@NotNull
	private final TeamService teamService;

	public VaultAccessService(@NotNull DataRepository<IVault, String> vaultRepository, @NotNull TeamService teamService) {
		this.vaultRepository = vaultRepository;
		this.teamService = teamService;
	}

	@NotNull
	public List<IVault> findVaultsAccessibleToUser(@NotNull String userId) {
		List<IVault> accessibleVaults = new ArrayList<>();
		for (IVault vault : vaultRepository.findAll()) {
			if (isVaultAccessibleToUser(userId, vault)) {
				accessibleVaults.add(vault);
			}
		}
		return accessibleVaults;
	}

	@NotNull
	public IVault requireUserAccessibleVault(@NotNull String userId, @NotNull String vaultId) {
		IVault vault = vaultRepository.findById(vaultId);
		if (vault == null) {
			throw new EntityNotFoundException("The vault with id " + vaultId + " does not exist");
		}
		if (!isVaultAccessibleToUser(userId, vault)) {
			throw new IlleagalAccessException("The vault with id " + vaultId + " is not accessible to user " + userId);
		}
		return vault;
	}

	public void requireUserCanManageVault(@NotNull String userId, @NotNull IVault vault) {
		if (vault.getScope() == VaultScope.USER) {
			if (!userId.equals(vault.getScopeId())) {
				throw new IlleagalAccessException(
						"The vault with id " + vault.getId() + " is not managed by user " + userId);
			}
			return;
		}
		if (vault.getScope() == VaultScope.TEAM && !teamService.isUserOwnerOfTeam(vault.getScopeId(), userId)) {
			throw new IlleagalAccessException(
					"The vault with id " + vault.getId() + " is not manageable by user " + userId);
		}
	}

	private boolean isVaultAccessibleToUser(@NotNull String userId, @NotNull IVault vault) {
		if (vault.getScope() == VaultScope.USER) {
			return userId.equals(vault.getScopeId());
		}
		if (vault.getScope() == VaultScope.TEAM) {
			ITeam team = teamService.getTeam(vault.getScopeId());
			return teamService.isUserMemberOfTeam(team.getId(), userId);
		}
		return false;
	}
}
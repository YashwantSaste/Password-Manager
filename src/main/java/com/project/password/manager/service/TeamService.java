package com.project.password.manager.service;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.project.password.manager.configuration.application.Configuration;
import com.project.password.manager.database.DataRepository;
import com.project.password.manager.exceptions.EntityNotFoundException;
import com.project.password.manager.exceptions.IlleagalAccessException;
import com.project.password.manager.guice.PlatformEntityProvider;
import com.project.password.manager.model.ITeam;
import com.project.password.manager.model.IUser;
import com.project.password.manager.model.Status;
import com.project.password.manager.model.UserRole;
import com.project.password.manager.util.KeyGenerator;

public class TeamService {

	@NotNull
	private final DataRepository<ITeam, String> teamRepository;
	@NotNull
	private final UserService userService;

	public TeamService(@NotNull DataRepository<ITeam, String> teamRepository, @NotNull UserService userService) {
		this.teamRepository = teamRepository;
		this.userService = userService;
	}

	@NotNull
	public ITeam createTeam(@NotNull String teamName, @NotNull IUser initiatorUser) {
		assertUserCanCreateTeam(initiatorUser);
		if (teamRepository.findById(teamName) != null) {
			throw new IllegalArgumentException("Team already exists: " + teamName);
		}
		ITeam team = PlatformEntityProvider.getEntityProvider().getTeam();
		team.setId(teamName);
		team.setName(teamName);
		team.setOwners(List.of(initiatorUser));
		team.setMembers(List.of(initiatorUser));
		team.setDefaultVaultId("");
		team.setKeySalt(generateTeamSharedKeySalt());
		LocalDateTime currentTime = LocalDateTime.now();
		team.metadata().setCreatedAt(currentTime);
		team.metadata().setUpdatedAt(currentTime);
		team.metadata().setLastAccessedAt(currentTime);
		team.metadata().setStatus(Status.ACTIVE);
		teamRepository.save(team);
		return team;
	}

	@NotNull
	public ITeam getTeam(@NotNull String teamId) {
		ITeam team = teamRepository.findById(teamId);
		if (team == null) {
			throw new EntityNotFoundException("Team not found: " + teamId);
		}
		return team;
	}

	public boolean isUserMemberOfTeam(@NotNull String teamId, @NotNull String userId) {
		ITeam team = getTeam(teamId);
		return team.owners().stream().anyMatch(user -> userId.equals(user.getId()))
				|| team.memebers().stream().anyMatch(user -> userId.equals(user.getId()));
	}

	public boolean isUserOwnerOfTeam(@NotNull String teamId, @NotNull String userId) {
		ITeam team = getTeam(teamId);
		return team.owners().stream().anyMatch(user -> userId.equals(user.getId()));
	}

	public void saveOrUpdateTeam(@NotNull ITeam team) {
		if (teamRepository.findById(team.getId()) == null) {
			teamRepository.save(team);
			return;
		}
		teamRepository.update(team.getId(), team);
	}

	private void assertUserCanCreateTeam(@NotNull IUser user) {
		if (userService.getUser(user.getId()) == null) {
			throw new IlleagalAccessException("User does not exist.");
		}
		boolean onlyAdminCanCreate = Configuration.getInstance().teamConfiguration().onlyAdminCanCreate();
		if (onlyAdminCanCreate && !user.getRoles().contains(UserRole.ADMIN)) {
			throw new IlleagalAccessException("Only Admin is allowed to create a Team.");
		}
	}

	@NotNull
	private String generateTeamSharedKeySalt() {
		byte[] keyBytes = KeyGenerator.generateKeyFromPassword(UUID.randomUUID().toString()).getEncoded();
		return Base64.getEncoder().encodeToString(keyBytes);
	}
}

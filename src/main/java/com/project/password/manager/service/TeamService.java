package com.project.password.manager.service;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.configuration.application.Configuration;
import com.project.password.manager.database.DataRepository;
import com.project.password.manager.exceptions.EntityNotFoundException;
import com.project.password.manager.exceptions.IlleagalAccessException;
import com.project.password.manager.exceptions.UnauthorizedSessionException;
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
		String normalizedTeamId = normalizeTeamLookupValue(teamId);
		ITeam team = teamRepository.findById(normalizedTeamId);
		if (team == null) {
			for (ITeam candidate : teamRepository.findAll()) {
				if (normalizedTeamId.equals(normalizeTeamLookupValue(candidate.getId()))
						|| normalizedTeamId.equals(normalizeTeamLookupValue(candidate.name()))) {
					team = candidate;
					break;
				}
			}
		}
		if (team == null) {
			throw new EntityNotFoundException(buildTeamNotFoundMessage(normalizedTeamId));
		}
		return team;
	}

	@NotNull
	public List<ITeam> getTeamsForUser(@NotNull String userId) {
		List<ITeam> teams = new ArrayList<>();
		for (ITeam team : teamRepository.findAll()) {
			if (isUserMemberOfTeam(team, userId)) {
				teams.add(team);
			}
		}
		return teams;
	}

	@NotNull
	public ITeam requireTeamAccessibleToUser(@NotNull String teamId, @NotNull String userId) {
		ITeam team = getTeam(teamId);
		if (!isUserMemberOfTeam(team, userId)) {
			throw new UnauthorizedSessionException("The team with id " + teamId + " is not accessible to user " + userId);
		}
		return team;
	}

	public boolean isUserMemberOfTeam(@NotNull String teamId, @NotNull String userId) {
		return isUserMemberOfTeam(getTeam(teamId), userId);
	}

	public boolean isUserOwnerOfTeam(@NotNull String teamId, @NotNull String userId) {
		return isUserOwnerOfTeam(getTeam(teamId), userId);
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

	private boolean isUserMemberOfTeam(@NotNull ITeam team, @NotNull String userId) {
		return isUserOwnerOfTeam(team, userId)
				|| team.memebers().stream().anyMatch(user -> userId.equals(user.getId()));
	}

	private boolean isUserOwnerOfTeam(@NotNull ITeam team, @NotNull String userId) {
		return team.owners().stream().anyMatch(user -> userId.equals(user.getId()));
	}

	@NotNull
	private String buildTeamNotFoundMessage(@NotNull String teamId) {
		List<String> availableTeamIds = teamRepository.findAll().stream()
				.map(ITeam::getId)
				.sorted(String.CASE_INSENSITIVE_ORDER)
				.collect(Collectors.toList());
		if (availableTeamIds.isEmpty()) {
			return "Team not found: " + teamId;
		}
		return "Team not found: " + teamId + ". Available teams: " + String.join(", ", availableTeamIds);
	}

	@NotNull
	private String normalizeTeamLookupValue(@NotNull String value) {
		StringBuilder builder = new StringBuilder(value.length());
		for (int index = 0; index < value.length(); index++) {
			char currentChar = value.charAt(index);
			if (!Character.isISOControl(currentChar)) {
				builder.append(currentChar);
			}
		}
		return builder.toString().trim().toLowerCase();
	}
}

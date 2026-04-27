package com.project.password.manager.service;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.configuration.application.Configuration;
import com.project.password.manager.database.DataRepository;
import com.project.password.manager.exceptions.InvalidAccessException;
import com.project.password.manager.guice.PlatformEntityProvider;
import com.project.password.manager.model.ITeam;
import com.project.password.manager.model.IUser;
import com.project.password.manager.model.UserRole;

public class TeamService {

	@NotNull
	private final DataRepository<ITeam, String> teamRepository;
	@NotNull
	private final UserService userService;
	@NotNull
	private final VaultService vaultService;

	public TeamService(@NotNull DataRepository<ITeam, String> teamRepository, @NotNull UserService userService,
			@NotNull VaultService vaultService) {
		this.teamRepository = teamRepository;
		this.userService = userService;
		this.vaultService = vaultService;
	}

	public void createTeam(@NotNull String teamName, @NotNull IUser initiatorUser) {
		checkUserCanCreate(initiatorUser);
		ITeam team = PlatformEntityProvider.getEntityProvider().getTeam();
		team.setId(teamName);
		team.setName(teamName);
		team.setOwners(List.of(initiatorUser));
	}

	private void checkUserCanCreate(@NotNull IUser user) {
		if (userService.getUser(user.getId()) == null) {
			throw new InvalidAccessException("User does not exist.");
		}
		boolean onlyAdminCanCreate = Configuration.getInstance().teamConfiguration().onlyAdminCanCreate();
		if (onlyAdminCanCreate && !user.getRoles().contains(UserRole.ADMIN)) {
			throw new InvalidAccessException("Only Admin is allowed to create a Team.");
		}
	}
}

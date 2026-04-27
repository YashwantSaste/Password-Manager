package com.project.password.manager.cli.handlers.team;

import org.jetbrains.annotations.NotNull;

import com.google.inject.Inject;
import com.project.password.manager.cli.commands.team.CreateTeamCommand;
import com.project.password.manager.cli.handlers.AbstractAuthorizedCommandHandler;
import com.project.password.manager.cli.runtime.CliOutput;
import com.project.password.manager.cli.runtime.CliSession;
import com.project.password.manager.cli.runtime.CliTheme;
import com.project.password.manager.middleware.RequireAuthorization;
import com.project.password.manager.model.ITeam;
import com.project.password.manager.service.TeamService;
import com.project.password.manager.service.UserService;

public class CreateTeamCommandHandler extends AbstractAuthorizedCommandHandler<CreateTeamCommand.Request> {

	@NotNull
	private final TeamService teamService;

	@Inject
	public CreateTeamCommandHandler(@NotNull CliSession session, @NotNull UserService userService,
			@NotNull TeamService teamService, @NotNull CliOutput output) {
		super(session, userService, output);
		this.teamService = teamService;
	}

	@Override
	@RequireAuthorization
	public void handle(@NotNull CreateTeamCommand.Request request) {
		ITeam team = teamService.createTeam(request.getTeamName(), currentUser());
		output.info(CliTheme.success("Created team " + team.name()));
	}
}
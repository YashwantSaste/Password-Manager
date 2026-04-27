package com.project.password.manager.cli.handlers.team;

import org.jetbrains.annotations.NotNull;

import com.google.inject.Inject;
import com.project.password.manager.cli.commands.team.GetTeamCommand;
import com.project.password.manager.cli.handlers.AbstractAuthorizedCommandHandler;
import com.project.password.manager.cli.handlers.CliViewPrinter;
import com.project.password.manager.cli.runtime.CliOutput;
import com.project.password.manager.cli.runtime.CliSession;
import com.project.password.manager.middleware.RequireAuthorization;
import com.project.password.manager.model.ITeam;
import com.project.password.manager.service.TeamService;
import com.project.password.manager.service.UserService;

public class GetTeamCommandHandler extends AbstractAuthorizedCommandHandler<GetTeamCommand.Request> {

	@NotNull
	private final TeamService teamService;

	@Inject
	public GetTeamCommandHandler(@NotNull CliSession session, @NotNull UserService userService,
			@NotNull TeamService teamService, @NotNull CliOutput output) {
		super(session, userService, output);
		this.teamService = teamService;
	}

	@Override
	@RequireAuthorization
	public void handle(@NotNull GetTeamCommand.Request request) {
		ITeam team = teamService.requireTeamAccessibleToUser(request.getTeamId(), currentUserId());
		output.info(CliViewPrinter.formatTeam(team));
	}
}
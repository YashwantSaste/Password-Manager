package com.project.password.manager.cli.commands.team;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.cli.commands.DelegatingCliCommand;
import com.project.password.manager.cli.handlers.team.ListTeamCommandHandler;

import picocli.CommandLine.Command;

@Command(name = "list", mixinStandardHelpOptions = true, description = "Lists teams accessible to the authenticated user.")
public class ListTeamCommand extends DelegatingCliCommand<ListTeamCommand.Request, ListTeamCommandHandler> {

	@Override
	@NotNull
	protected Request buildRequest() {
		return new Request();
	}

	public static final class Request {
	}

}

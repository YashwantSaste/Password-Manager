package com.project.password.manager.cli.commands.group;

import jakarta.validation.constraints.NotNull;

import com.project.password.manager.cli.commands.DelegatingCliCommand;
import com.project.password.manager.cli.handlers.group.UserGroupListCommandHandler;

import picocli.CommandLine.Command;

@Command(name = "list", mixinStandardHelpOptions = true, description = "Lists user groups.")
public class UserGroupListCommand extends DelegatingCliCommand<UserGroupListCommand.Request, UserGroupListCommandHandler> {

	@Override
	@NotNull
	protected Request buildRequest() {
		return new Request();
	}

	public static final class Request {
	}
}

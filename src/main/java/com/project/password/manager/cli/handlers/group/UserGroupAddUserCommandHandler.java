package com.project.password.manager.cli.handlers.group;

import jakarta.validation.constraints.NotNull;

import com.google.inject.Inject;
import com.project.password.manager.cli.commands.group.UserGroupAddUserCommand;
import com.project.password.manager.cli.handlers.AbstractAuthorizedCommandHandler;
import com.project.password.manager.cli.handlers.CliViewPrinter;
import com.project.password.manager.cli.runtime.CliOutput;
import com.project.password.manager.cli.runtime.CliSession;
import com.project.password.manager.service.UserGroupService;
import com.project.password.manager.service.UserService;

public class UserGroupAddUserCommandHandler extends AbstractAuthorizedCommandHandler<UserGroupAddUserCommand.Request> {

	@NotNull
	private final UserGroupService userGroupService;

	@Inject
	public UserGroupAddUserCommandHandler(@NotNull CliSession session, @NotNull UserService userService,
			@NotNull UserGroupService userGroupService, @NotNull CliOutput output) {
		super(session, userService, output);
		this.userGroupService = userGroupService;
	}

	@Override
	public void handle(@NotNull UserGroupAddUserCommand.Request request) {
		output.info(CliViewPrinter.formatGroup(userGroupService.addUser(request.getGroupId(), request.getUserId())));
	}
}

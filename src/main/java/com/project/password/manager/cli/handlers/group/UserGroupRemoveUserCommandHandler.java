package com.project.password.manager.cli.handlers.group;

import jakarta.validation.constraints.NotNull;

import com.google.inject.Inject;
import com.project.password.manager.cli.commands.group.UserGroupRemoveUserCommand;
import com.project.password.manager.cli.handlers.AbstractAuthorizedCommandHandler;
import com.project.password.manager.cli.handlers.CliViewPrinter;
import com.project.password.manager.cli.runtime.CliOutput;
import com.project.password.manager.cli.runtime.CliSession;
import com.project.password.manager.service.UserGroupService;
import com.project.password.manager.service.UserService;

public class UserGroupRemoveUserCommandHandler
		extends AbstractAuthorizedCommandHandler<UserGroupRemoveUserCommand.Request> {

	@NotNull
	private final UserGroupService userGroupService;

	@Inject
	public UserGroupRemoveUserCommandHandler(@NotNull CliSession session, @NotNull UserService userService,
			@NotNull UserGroupService userGroupService, @NotNull CliOutput output) {
		super(session, userService, output);
		this.userGroupService = userGroupService;
	}

	@Override
	public void handle(@NotNull UserGroupRemoveUserCommand.Request request) {
		output.info(CliViewPrinter.formatGroup(userGroupService.removeUser(request.getGroupId(), request.getUserId())));
	}
}

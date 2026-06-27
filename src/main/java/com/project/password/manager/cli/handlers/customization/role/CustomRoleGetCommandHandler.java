package com.project.password.manager.cli.handlers.customization.role;

import jakarta.validation.constraints.NotNull;

import com.google.inject.Inject;
import com.project.password.manager.cli.commands.customization.role.CustomRoleGetCommand;
import com.project.password.manager.cli.commands.customization.role.CustomRoleGetCommand.Request;
import com.project.password.manager.cli.handlers.AbstractAuthorizedCommandHandler;
import com.project.password.manager.cli.handlers.CliViewPrinter;
import com.project.password.manager.cli.runtime.CliOutput;
import com.project.password.manager.cli.runtime.CliSession;
import com.project.password.manager.service.CustomRoleService;
import com.project.password.manager.service.UserService;

public class CustomRoleGetCommandHandler extends AbstractAuthorizedCommandHandler<CustomRoleGetCommand.Request> {

	@NotNull
	private final CustomRoleService customRoleService;

	@Inject
	public CustomRoleGetCommandHandler(@NotNull CliSession session, @NotNull UserService userService,
			@NotNull CustomRoleService customRoleService, @NotNull CliOutput output) {
		super(session, userService, output);
		this.customRoleService = customRoleService;
	}

	@Override
	public void handle(@NotNull Request request) {
		output.info(CliViewPrinter.formatRole(customRoleService.getCustomRole(request.getRoleId())));
	}

}

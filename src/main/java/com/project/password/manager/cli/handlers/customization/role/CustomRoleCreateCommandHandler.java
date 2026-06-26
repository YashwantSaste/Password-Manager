package com.project.password.manager.cli.handlers.customization.role;

import org.jetbrains.annotations.NotNull;

import com.google.inject.Inject;
import com.project.password.manager.cli.commands.customization.role.CustomRoleCreateCommand;
import com.project.password.manager.cli.commands.customization.role.CustomRoleCreateCommand.Request;
import com.project.password.manager.cli.handlers.AbstractAuthorizedCommandHandler;
import com.project.password.manager.cli.runtime.CliOutput;
import com.project.password.manager.cli.runtime.CliSession;
import com.project.password.manager.service.CustomRoleService;
import com.project.password.manager.service.UserService;

public class CustomRoleCreateCommandHandler extends AbstractAuthorizedCommandHandler<CustomRoleCreateCommand.Request> {

	@NotNull
	private final CustomRoleService customRoleService;

	@Inject
	public CustomRoleCreateCommandHandler(@NotNull CliSession session, @NotNull UserService userService,
			@NotNull CustomRoleService customRoleService, @NotNull CliOutput output) {
		super(session, userService, output);
		this.customRoleService = customRoleService;
	}

	@Override
	public void handle(@NotNull Request request) {
		customRoleService.createCustomRole(request.getRoleId(), request.getRoleName());
	}

}

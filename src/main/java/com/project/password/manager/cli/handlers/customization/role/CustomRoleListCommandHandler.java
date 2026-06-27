package com.project.password.manager.cli.handlers.customization.role;

import java.util.List;

import jakarta.validation.constraints.NotNull;

import com.google.inject.Inject;
import com.project.password.manager.cli.commands.customization.role.CustomRoleListCommand;
import com.project.password.manager.cli.handlers.AbstractAuthorizedCommandHandler;
import com.project.password.manager.cli.handlers.CliViewPrinter;
import com.project.password.manager.cli.runtime.CliOutput;
import com.project.password.manager.cli.runtime.CliSession;
import com.project.password.manager.model.ICustomRole;
import com.project.password.manager.service.CustomRoleService;
import com.project.password.manager.service.UserService;

public class CustomRoleListCommandHandler extends AbstractAuthorizedCommandHandler<CustomRoleListCommand.Request> {

	@NotNull
	private final CliOutput output;
	@NotNull
	private final CustomRoleService customRoleService;

	@Inject
	public CustomRoleListCommandHandler(@NotNull CliSession session, @NotNull UserService userService,
			@NotNull CustomRoleService customRoleService, @NotNull CliOutput output) {
		super(session, userService, output);
		this.customRoleService = customRoleService;
		this.output = output;
	}

	@Override
	public void handle(@NotNull CustomRoleListCommand.Request request) {
		List<ICustomRole> rolesResult = customRoleService.getCustomRolesByIds(request.getRoleIds());
		output.info(CliViewPrinter.formatRoles(rolesResult));
	}
}

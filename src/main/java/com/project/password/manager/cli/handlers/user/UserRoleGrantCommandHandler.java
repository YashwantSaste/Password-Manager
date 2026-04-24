package com.project.password.manager.cli.handlers.user;

import org.jetbrains.annotations.NotNull;

import com.google.inject.Inject;
import com.project.password.manager.cli.commands.user.UserRoleGrantCommand;
import com.project.password.manager.cli.handlers.AbstractAuthorizedCommandHandler;
import com.project.password.manager.cli.runtime.CliOutput;
import com.project.password.manager.cli.runtime.CliSession;
import com.project.password.manager.cli.runtime.CliTheme;
import com.project.password.manager.middleware.RequireAuthorization;
import com.project.password.manager.model.UserRole;
import com.project.password.manager.service.UserService;

public class UserRoleGrantCommandHandler extends AbstractAuthorizedCommandHandler<UserRoleGrantCommand.Request> {

	@NotNull
	private final UserService userService;

	@Inject
	public UserRoleGrantCommandHandler(@NotNull CliSession session, @NotNull UserService userService,
			@NotNull CliOutput output) {
		super(session, userService, output);
		this.userService = userService;
	}

	@Override
	@RequireAuthorization(roles = { UserRole.ADMIN })
	public void handle(@NotNull UserRoleGrantCommand.Request request) {
		UserRole role = parseRole(request.getRole());
		boolean changed = userService.grantRole(currentUserId(), request.getUsername(), role);
		String status = changed ? "granted" : "already assigned";
		output.info(CliTheme.successPanel("Role Updated",
				CliTheme.key("user") + CliTheme.muted(" : ") + CliTheme.secondary(request.getUsername()),
				CliTheme.key("role") + CliTheme.muted(" : ") + CliTheme.accent(role.name()),
				CliTheme.key("status") + CliTheme.muted(" : ") + CliTheme.secondary(status)));
	}

	@NotNull
	private UserRole parseRole(@NotNull String roleName) {
		for (UserRole role : UserRole.values()) {
			if (role.name().equalsIgnoreCase(roleName)) {
				return role;
			}
		}
		throw new IllegalArgumentException("Unsupported role: " + roleName + ". Supported roles: USER, ADMIN");
	}
}
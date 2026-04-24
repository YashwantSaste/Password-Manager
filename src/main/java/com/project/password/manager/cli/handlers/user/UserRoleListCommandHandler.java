package com.project.password.manager.cli.handlers.user;

import java.util.List;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;

import com.google.inject.Inject;
import com.project.password.manager.cli.commands.user.UserRoleListCommand;
import com.project.password.manager.cli.handlers.AbstractAuthorizedCommandHandler;
import com.project.password.manager.cli.runtime.CliOutput;
import com.project.password.manager.cli.runtime.CliSession;
import com.project.password.manager.cli.runtime.CliTheme;
import com.project.password.manager.middleware.RequireAuthorization;
import com.project.password.manager.model.IUser;
import com.project.password.manager.model.UserRole;
import com.project.password.manager.service.UserService;

public class UserRoleListCommandHandler extends AbstractAuthorizedCommandHandler<UserRoleListCommand.Request> {

	@NotNull
	private final UserService userService;

	@Inject
	public UserRoleListCommandHandler(@NotNull CliSession session, @NotNull UserService userService,
			@NotNull CliOutput output) {
		super(session, userService, output);
		this.userService = userService;
	}

	@Override
	@RequireAuthorization(roles = { UserRole.ADMIN })
	public void handle(@NotNull UserRoleListCommand.Request request) {
		IUser user = requireUser(request.getUsername());
		output.info(CliTheme.infoPanel("User Roles",
				CliTheme.key("user") + CliTheme.muted(" : ") + CliTheme.secondary(user.getName()),
				CliTheme.key("roles") + CliTheme.muted(" : ") + CliTheme.accent(joinRoles(user.getRoles()))));
	}

	@NotNull
	private IUser requireUser(@NotNull String username) {
		IUser user = userService.getUser(username);
		if (user == null) {
			throw new IllegalArgumentException("User not found: " + username);
		}
		return user;
	}

	@NotNull
	private String joinRoles(@NotNull List<UserRole> roles) {
		if (roles.isEmpty()) {
			return UserRole.USER.name();
		}
		return roles.stream().map(UserRole::name).collect(Collectors.joining(", "));
	}
}
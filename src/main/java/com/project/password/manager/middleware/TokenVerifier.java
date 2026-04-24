package com.project.password.manager.middleware;

import java.util.Arrays;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.google.inject.Inject;
import com.project.password.manager.cli.runtime.CliSession;
import com.project.password.manager.exceptions.UnauthorizedSessionException;
import com.project.password.manager.model.IUser;
import com.project.password.manager.model.UserRole;
import com.project.password.manager.service.TokenService;
import com.project.password.manager.service.UserService;

public class TokenVerifier {

	private static final String UNAUTHORIZED_ACCESS_MESSAGE = "Session expired. Please login again.";
	@NotNull
	private final TokenService tokenService;
	@NotNull
	private final UserService userService;
	@NotNull
	private final CliSession session;

	@Inject
	public TokenVerifier(@NotNull TokenService tokenService, @NotNull UserService userService,
			@NotNull CliSession session) {
		this.tokenService = tokenService;
		this.userService = userService;
		this.session = session;
	}

	public void validateCurrentSession(@NotNull UserRole... requiredRoles) {
		String userId = session.requireUserId();
		String rawToken = session.requireToken();
		IUser user = userService.getUser(userId);
		if (user == null) {
			session.clear();
			throw new UnauthorizedSessionException("Authenticated user no longer exists.");
		}
		String persistedToken = tokenService.getToken(user);
		if (persistedToken == null || !persistedToken.equals(rawToken)) {
			session.clear();
			throw new UnauthorizedSessionException(UNAUTHORIZED_ACCESS_MESSAGE);
		}
		try {
			if (tokenService.verify(rawToken, user) == null) {
				session.clear();
				throw new UnauthorizedSessionException(UNAUTHORIZED_ACCESS_MESSAGE);
			}
		} catch (JWTVerificationException ex) {
			session.clear();
			throw new UnauthorizedSessionException(UNAUTHORIZED_ACCESS_MESSAGE);
		}
		if (requiredRoles.length > 0 && !hasAnyRequiredRole(user, requiredRoles)) {
			throw new UnauthorizedSessionException(
					"Access denied. Required role: " + Arrays.toString(requiredRoles));
		}
	}

	private boolean hasAnyRequiredRole(@NotNull IUser user, @NotNull UserRole... requiredRoles) {
		List<UserRole> assignedRoles = user.getRoles();
		if (assignedRoles.isEmpty()) {
			assignedRoles = List.of(UserRole.USER);
		}
		for (UserRole requiredRole : requiredRoles) {
			if (assignedRoles.contains(requiredRole)) {
				return true;
			}
		}
		return false;
	}
}

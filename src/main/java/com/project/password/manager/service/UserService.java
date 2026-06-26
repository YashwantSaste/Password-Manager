package com.project.password.manager.service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.project.password.manager.database.DataRepository;
import com.project.password.manager.event.EntityChangeDetector;
import com.project.password.manager.event.EntityEventFactory;
import com.project.password.manager.event.EntityEventSupport;
import com.project.password.manager.event.EntitySnapshotter;
import com.project.password.manager.event.EventDispatcher;
import com.project.password.manager.event.EventLogger;
import com.project.password.manager.event.IEntityEventSupport;
import com.project.password.manager.event.listener.EventLoggingListener;
import com.project.password.manager.model.IUser;
import com.project.password.manager.model.UserRole;
import com.project.password.manager.util.Logger;
import com.project.password.manager.util.ModelObjectMapperFactory;

public class UserService {

	private static final Logger log = Logger.getLogger(UserService.class);

	@NotNull
	private final DataRepository<IUser, String> userRepository;
	@Nullable
	private final TokenService tokenService;
	@NotNull
	private final IEntityEventSupport eventSupport;
	@NotNull
	private final CustomRoleService customRoleService;

	public UserService(@NotNull DataRepository<IUser, String> userRepository) {
		this(userRepository, null, null,
				new EntityEventSupport(new EntitySnapshotter(ModelObjectMapperFactory.create()),
						new EntityEventFactory(new EntityChangeDetector(ModelObjectMapperFactory.create())),
						new EventDispatcher(List.of(new EventLoggingListener(new EventLogger())))));
	}

	public UserService(@NotNull DataRepository<IUser, String> userRepository, @Nullable TokenService tokenService,
			@NotNull CustomRoleService customRoleService) {
		this(userRepository, tokenService, customRoleService,
				new EntityEventSupport(new EntitySnapshotter(ModelObjectMapperFactory.create()),
						new EntityEventFactory(new EntityChangeDetector(ModelObjectMapperFactory.create())),
						new EventDispatcher(List.of(new EventLoggingListener(new EventLogger())))));
	}

	public UserService(@NotNull DataRepository<IUser, String> userRepository, @Nullable TokenService tokenService,
			@NotNull CustomRoleService customRoleService, @NotNull IEntityEventSupport eventSupport) {
		this.userRepository = userRepository;
		this.tokenService = tokenService;
		this.eventSupport = eventSupport;
		this.customRoleService = customRoleService;
	}

	@Nullable
	public IUser getUser(@NotNull String id) {
		return userRepository.findById(id);
	}

	@NotNull
	public List<IUser> getUsers() {
		return List.copyOf(userRepository.findAll());
	}

	public void saveUser(@NotNull IUser user) {
		userRepository.save(user);
		eventSupport.publishCreated(user);
	}

	public boolean grantRole(@NotNull String actorUserId, @NotNull String targetUserId, @NotNull UserRole role) {
		IUser actor = requireUser(actorUserId);
		IUser target = requireUser(targetUserId);
		IUser beforeSnapshot = eventSupport.snapshot(target);
		if (actor.getId().equals(target.getId()) && role == UserRole.ADMIN && !hasRole(target, UserRole.ADMIN)) {
			throw new IllegalArgumentException("Users cannot grant themselves the ADMIN role.");
		}
		List<UserRole> updatedRoles = normalizedRoles(target);
		if (updatedRoles.contains(role)) {
			return false;
		}
		updatedRoles.add(role);
		persistRoles(target, updatedRoles, beforeSnapshot);
		log.info("Role granted by user [" + actor.getId() + "] to user [" + target.getId() + "] for role [" + role
				+ "]");
		return true;
	}

	public boolean revokeRole(@NotNull String actorUserId, @NotNull String targetUserId, @NotNull UserRole role) {
		IUser actor = requireUser(actorUserId);
		IUser target = requireUser(targetUserId);
		IUser beforeSnapshot = eventSupport.snapshot(target);
		List<UserRole> updatedRoles = normalizedRoles(target);
		if (!updatedRoles.contains(role)) {
			return false;
		}
		if (role == UserRole.ADMIN && isLastAdmin(target)) {
			throw new IllegalArgumentException("Cannot remove ADMIN from the last remaining admin user.");
		}
		while (updatedRoles.remove(role)) {
			// Remove duplicate assignments if older data introduced them.
		}
		persistRoles(target, updatedRoles, beforeSnapshot);
		log.info("Role revoked by user [" + actor.getId() + "] from user [" + target.getId() + "] for role [" + role
				+ "]");
		return true;
	}

	private void persistRoles(@NotNull IUser user, @NotNull List<UserRole> roles, @NotNull IUser beforeSnapshot) {
		user.setRoles(normalizeRoles(roles));
		userRepository.update(user.getId(), user);
		eventSupport.publishUpdated(beforeSnapshot, user);
		if (tokenService != null) {
			tokenService.revokeToken(user);
		}
	}

	@NotNull
	public IUser requireUser(@NotNull String userId) {
		IUser user = userRepository.findById(userId);
		if (user == null) {
			throw new IllegalArgumentException("User not found: " + userId);
		}
		return user;
	}

	private boolean isLastAdmin(@NotNull IUser user) {
		if (!hasRole(user, UserRole.ADMIN)) {
			return false;
		}
		long adminCount = getUsers().stream().filter(existingUser -> hasRole(existingUser, UserRole.ADMIN)).count();
		return adminCount <= 1;
	}

	private boolean hasRole(@NotNull IUser user, @NotNull UserRole role) {
		List<UserRole> roles = user.getRoles();
		if (roles.isEmpty()) {
			return role == UserRole.USER;
		}
		return roles.contains(role);
	}

	@NotNull
	private List<UserRole> normalizedRoles(@NotNull IUser user) {
		return new ArrayList<>(normalizeRoles(user.getRoles()));
	}

	@NotNull
	private List<UserRole> normalizeRoles(@NotNull List<UserRole> roles) {
		Set<UserRole> normalizedRoles = new LinkedHashSet<>(roles);
		if (normalizedRoles.isEmpty()) {
			normalizedRoles.add(UserRole.USER);
		}
		return new ArrayList<>(normalizedRoles);
	}
}

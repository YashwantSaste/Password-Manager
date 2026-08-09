package com.project.password.manager.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.project.password.manager.database.DataRepository;
import com.project.password.manager.event.IEntityEventSupport;
import com.project.password.manager.exceptions.EntityNotFoundException;
import com.project.password.manager.guice.Platform;
import com.project.password.manager.model.ICustomRole;
import com.project.password.manager.model.IUser;
import com.project.password.manager.model.scope.IScope;
import com.project.password.manager.permission.BasePermission;
import com.project.password.manager.permission.IPermission;
import com.project.password.manager.util.ValidationUtils;
import com.project.password.manager.validation.utlis.UserValidationUtils;

import jakarta.validation.constraints.NotNull;

public class CustomRoleService {

	private final DataRepository<ICustomRole, String> customRoleRepository;
	private final DataRepository<IUser, String> userRepository;
	private final IEntityEventSupport eventSupport;

	public CustomRoleService(@NotNull DataRepository<ICustomRole, String> customRoleRepository,
			@NotNull DataRepository<IUser, String> userRepository, @NotNull IEntityEventSupport eventSupport) {
		this.customRoleRepository = customRoleRepository;
		this.userRepository = userRepository;
		this.eventSupport = eventSupport;
	}

	@NotNull
	public List<ICustomRole> getAllCustomRoles() {
		return customRoleRepository.findAll();
	}

	@NotNull
	public List<ICustomRole> getCustomRolesByIds(@NotNull String[] roleIds) {
		if (roleIds.length == 0) {
			return getAllCustomRoles();
		}
		List<ICustomRole> roles = new ArrayList<>();
		for (String roleId : roleIds) {
			roles.add(getCustomRole(roleId));
		}
		return roles;
	}

	@NotNull
	public ICustomRole getCustomRole(@NotNull String roleId) {
		ICustomRole role = customRoleRepository.findById(roleId);
		if (role == null) {
			throw new EntityNotFoundException("Custom role not found: " + roleId);
		}
		return role;
	}

	public void createCustomRole(@NotNull String roleId, @NotNull String roleName) {
		createCustomRole(roleId, roleName, new BasePermission(), null);
	}

	public void createCustomRole(@NotNull String roleId, @NotNull String roleName, @NotNull IPermission permission,
			@Nullable IScope scope) {
		String normalizedRoleId = ValidationUtils.requireText(roleId, "Role id is required.");
		String normalizedRoleName = ValidationUtils.requireText(roleName, "Role name is required.");
		if (customRoleRepository.findById(normalizedRoleId) != null) {
			throw new IllegalArgumentException("Custom role already exists: " + normalizedRoleId);
		}
		ICustomRole newRole = Platform.getPlatformContext().getCustomRole();
		newRole.setId(normalizedRoleId);
		newRole.setRoleName(normalizedRoleName);
		newRole.setPermission(permission);
		newRole.setScope(scope);
		customRoleRepository.save(newRole);
		eventSupport.publishCreated(newRole);
	}

	public void updateRole(@NotNull String roleId, @NotNull ICustomRole updatedRole) {
		ICustomRole before = eventSupport.snapshot(getCustomRole(roleId));
		customRoleRepository.update(roleId, updatedRole);
		eventSupport.publishUpdated(before, updatedRole);
	}

	public void deleteRole(@NotNull String roleId) {
		ICustomRole role = getCustomRole(roleId);
		customRoleRepository.delete(roleId);
		eventSupport.publishDeleted(role);
	}

	public boolean grantCustomRolesToUser(@NotNull String userId, @NotNull String[] customRoleIds) {
		try {
			IUser user = UserValidationUtils.requireUser(userRepository, userId);
			List<ICustomRole> customRoles = getCustomRolesByIds(customRoleIds);
			user.setCustomRoles(customRoles);
			userRepository.update(userId, user);
			return true;
		} catch (Exception ex) {
			return false;
		}

	}

}

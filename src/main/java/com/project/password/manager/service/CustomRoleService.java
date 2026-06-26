package com.project.password.manager.service;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.database.DataRepository;
import com.project.password.manager.guice.PlatformEntityProvider;
import com.project.password.manager.model.ICustomRole;
import com.project.password.manager.model.IUser;
import com.project.password.manager.validation.utlis.UserValidationUtils;

public class CustomRoleService {

	private final DataRepository<ICustomRole, String> customRoleRepository;
	private final DataRepository<IUser, String> userRepository;

	public CustomRoleService(@NotNull DataRepository<ICustomRole, String> customRoleRepository,
			@NotNull DataRepository<IUser, String> userRepository) {
		this.customRoleRepository = customRoleRepository;
		this.userRepository = userRepository;
	}

	@NotNull
	public List<ICustomRole> getAllCustomRoles() {
		return customRoleRepository.findAll();
	}

	@NotNull
	public List<ICustomRole> getCustomRolesByIds(@NotNull String[] roleIds) {
		List<ICustomRole> roles = new ArrayList<>();
		for (String roleId : roleIds) {
			roles.add(getCustomRole(roleId));
		}
		return roles;
	}

	@NotNull
	public ICustomRole getCustomRole(@NotNull String roleId) {
		return customRoleRepository.findById(roleId);
	}

	public void createCustomRole(@NotNull String roleId, @NotNull String roleName) {
		ICustomRole newRole = PlatformEntityProvider.getEntityProvider().getCustomRole();
		newRole.setId(roleId);
		newRole.setRoleName(roleName);
		customRoleRepository.save(newRole);
	}

	public void updateRole(@NotNull String roleId, @NotNull ICustomRole updatedRole) {
		customRoleRepository.update(roleId, updatedRole);
	}

	public void deleteRole(@NotNull String roleId) {
		customRoleRepository.delete(roleId);
	}

	public boolean grantCustomRolesToUser(@NotNull String userId, @NotNull String[] customRoleIds) {
		try {
			IUser user = UserValidationUtils.requireUser(userId);
			List<ICustomRole> customRoles = getCustomRolesByIds(customRoleIds);
			user.setCustomRoles(customRoles);
			userRepository.update(userId, user);
			return true;
		} catch (Exception ex) {
			return false;
		}

	}

}

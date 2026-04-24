package com.project.password.manager.model.database.sql;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.model.IMetadata;
import com.project.password.manager.model.IUser;
import com.project.password.manager.model.UserRole;
import com.project.password.manager.model.IVault;
import com.project.password.manager.model.database.file.storage.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "Users")
public class JpaUser implements IUser {

	@Id
	@Column(nullable = false, updatable = false)
	private String id;
	private String name;

	@PrePersist
	void generateId() {
		if (id == null) {
			id = UUID.randomUUID().toString();
		}
	}

	protected JpaUser() {
		// for hibernate
	}

	public JpaUser(String id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	@NotNull
	public String getId() {
		return id;
	}

	@Override
	@NotNull
	public String getName() {
		return name;
	}

	@NotNull
	public User toDomain() {
		return new User();
	}

	@NotNull
	public static JpaUser from(@NotNull User user) {
		JpaUser jpaUser = new JpaUser();
		jpaUser.id = user.getId();
		jpaUser.name = user.getName();
		return jpaUser;
	}

	@Override
	public @NotNull String getAuthVerifier() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @NotNull String getDefaultVaultId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @NotNull List<IVault> getVaults() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @NotNull List<UserRole> getRoles() {
		return new ArrayList<>();
	}

	@Override
	public @NotNull IMetadata metadata() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @NotNull String getKeySalt() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMetadata(@NotNull IMetadata metadata) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setId(@NotNull String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setName(@NotNull String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setAuthVerifier(@NotNull String authVerifier) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setKeySalt(@NotNull String keySalt) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDefaultVaultId(@NotNull String defaultVaultId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setVaults(@NotNull List<IVault> vaults) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setRoles(@NotNull List<UserRole> roles) {
		// TODO Auto-generated method stub

	}
}

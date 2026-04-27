package com.project.password.manager.model.database.sql;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.model.IMetadata;
import com.project.password.manager.model.IUser;
import com.project.password.manager.model.IVault;
import com.project.password.manager.model.UserRole;
import com.project.password.manager.model.database.file.storage.Metadata;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "Users")
public class JpaUser implements IUser {

	@Id
	@Column(nullable = false, updatable = false)
	private String id;

	@Column(nullable = false, unique = true)
	private String name;

	@Column(nullable = false, length = 1024)
	private String authVerifier;

	@Column(nullable = false, length = 1024)
	private String keySalt;

	@Column
	private String defaultVaultId;

	@OneToMany(fetch = FetchType.EAGER, targetEntity = JpaVault.class)
	@JoinColumn(name = "userId", referencedColumnName = "id")
	private List<JpaVault> vaults = new ArrayList<>();

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "UserRoles", joinColumns = @JoinColumn(name = "userId"))
	@Column(name = "role", nullable = false)
	@Enumerated(EnumType.STRING)
	private List<UserRole> roles = new ArrayList<>();

	@Transient
	private IMetadata metadata = new Metadata();

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
		return name != null ? name : "";
	}

	@Override
	public @NotNull String getAuthVerifier() {
		return authVerifier != null ? authVerifier : "";
	}

	@Override
	public @NotNull String getDefaultVaultId() {
		return defaultVaultId != null ? defaultVaultId : "";
	}

	@Override
	public @NotNull List<IVault> getVaults() {
		return new ArrayList<>(vaults);
	}

	@Override
	public @NotNull List<UserRole> getRoles() {
		if (roles == null) {
			roles = new ArrayList<>();
		}
		return roles;
	}

	@Override
	@NotNull
	public IMetadata metadata() {
		if (metadata == null) {
			metadata = new Metadata();
		}
		return metadata;
	}

	@Override
	@NotNull
	public String getKeySalt() {
		return keySalt != null ? keySalt : "";
	}

	@Override
	public void setMetadata(@NotNull IMetadata metadata) {
		this.metadata = metadata;
	}

	@Override
	public void setId(@NotNull String id) {
		this.id = id;
	}

	@Override
	public void setName(@NotNull String name) {
		this.name = name;
	}

	@Override
	public void setAuthVerifier(@NotNull String authVerifier) {
		this.authVerifier = authVerifier;
	}

	@Override
	public void setKeySalt(@NotNull String keySalt) {
		this.keySalt = keySalt;
	}

	@Override
	public void setDefaultVaultId(@NotNull String defaultVaultId) {
		this.defaultVaultId = defaultVaultId;
	}

	@Override
	public void setVaults(@NotNull List<IVault> vaults) {
		this.vaults = new ArrayList<>();
		for (IVault vault : vaults) {
			this.vaults.add(JpaVault.from(vault));
		}
	}

	@Override
	public void setRoles(@NotNull List<UserRole> roles) {
		this.roles = new ArrayList<>(roles);
	}
}

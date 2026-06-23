package com.project.password.manager.model.database.sql;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.model.IMetadata;
import com.project.password.manager.model.IVault;
import com.project.password.manager.model.VaultScope;
import com.project.password.manager.model.database.file.storage.Metadata;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
@Table(name = "Vaults")
public class JpaVault implements IVault {

	@Id
	@Column(nullable = false, updatable = false)
	private String id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private VaultScope scope;
	@Column(nullable = false)
	private String scopeId;
	@Column(nullable = false, length = 8192)
	private String encryptedBlob;
	@Transient
	private IMetadata metadata = new Metadata();

	protected JpaVault() {
		// for hibernate
	}

	@NotNull
	public static JpaVault from(@NotNull IVault vault) {
		if (vault instanceof JpaVault jpaVault) {
			return jpaVault;
		}
		JpaVault entity = new JpaVault();
		entity.id = vault.getId();
		entity.name = vault.getName();
		entity.scope = vault.getScope();
		entity.scopeId = vault.getScopeId();
		entity.encryptedBlob = vault.getEncryptedBlob();
		entity.metadata = vault.metadata();
		return entity;
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
	@NotNull
	public VaultScope getScope() {
		return scope != null ? scope : VaultScope.USER;
	}

	@Override
	@NotNull
	public String getScopeId() {
		return scopeId != null ? scopeId : "";
	}

	@Override
	@NotNull
	public String getEncryptedBlob() {
		return encryptedBlob != null ? encryptedBlob : "";
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
	public void setId(@NotNull String vaultId) {
		this.id = vaultId;
	}

	@Override
	public void setName(@NotNull String vaultName) {
		this.name = vaultName;
	}

	@Override
	public void setScope(@NotNull VaultScope scope) {
		this.scope = scope;
	}

	@Override
	public void setScopeId(@NotNull String scopeId) {
		this.scopeId = scopeId;
	}

	@Override
	public void setEncryptedBlob(@NotNull String encryptedBlob) {
		this.encryptedBlob = encryptedBlob;
	}

	@Override
	public void setMetadata(@NotNull IMetadata metadata) {
		this.metadata = metadata;
	}
}
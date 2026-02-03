package com.project.password.manager.model.database.file.storage;

import java.time.LocalDateTime;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.model.IUser;
import com.project.password.manager.model.IVault;

public class User implements IUser {

	private String id;
	private String name;

	public User(String id, String name) {
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

	@Override
	public @NotNull String getAuthVerifier() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @NotNull String getSalt() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @NotNull String getDefaultVaultID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @NotNull List<IVault> getVaults() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @NotNull LocalDateTime createdAt() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @NotNull LocalDateTime updatedAt() {
		// TODO Auto-generated method stub
		return null;
	}

}

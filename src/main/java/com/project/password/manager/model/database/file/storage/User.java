package com.project.password.manager.model.database.file.storage;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.model.IMetadata;
import com.project.password.manager.model.IUser;
import com.project.password.manager.model.IVault;

public class User implements IUser, IFileStorableEntity {

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
	@NotNull
	public String getAuthVerifier() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@NotNull
	public String getLoginSalt() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@NotNull
	public String getDefaultVaultId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@NotNull
	public List<IVault> getVaults() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@NotNull
	public IMetadata metadata() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@NotNull
	public String getKeySalt() {
		return null;
	}

	@Override
	@NotNull
	public String getFileName() {
		return "user.json";
	}

}

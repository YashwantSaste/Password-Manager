package com.project.password.manager.model.database.nosql;

import java.time.LocalDateTime;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.model.IUser;
import com.project.password.manager.model.IVault;

public class UserDocument implements IUser {

	// Implementation will be added in the upcoming code

	@Override
	@NotNull
	public String getId() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	@NotNull
	public String getName() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
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

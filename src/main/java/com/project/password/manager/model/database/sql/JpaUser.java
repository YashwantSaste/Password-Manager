package com.project.password.manager.model.database.sql;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.model.IMetadata;
import com.project.password.manager.model.IUser;
import com.project.password.manager.model.IVault;
import com.project.password.manager.model.database.file.storage.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Users")
public class JpaUser implements IUser {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	private String name;

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
		return new User(id, name);
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
	public @NotNull IMetadata metadata() {
		// TODO Auto-generated method stub
		return null;
	}
}

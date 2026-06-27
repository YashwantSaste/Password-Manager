package com.project.password.manager.model.database.file.storage;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.password.manager.model.IMetadata;
import com.project.password.manager.model.IUserGroup;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserGroup implements IUserGroup, IFileStorableEntity {

	private String id;
	private String name;
	private List<String> users;
	private IMetadata metadata = new Metadata();

	public UserGroup() {
		// for jackson
	}

	public UserGroup(@NotNull String id, @NotNull String name, @NotNull List<String> users) {
		this.id = id;
		this.name = name;
		this.users = new ArrayList<>(users);
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
	public List<String> getUsers() {
		if (users == null) {
			users = new ArrayList<>();
		}
		return users;
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
	public void setUsers(@NotNull List<String> users) {
		this.users = new ArrayList<>(users);
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
	public void setMetadata(@NotNull IMetadata metadata) {
		this.metadata = metadata != null ? metadata : new Metadata();
	}

	@Override
	@NotNull
	public String getFileName() {
		return "group.json";
	}
}

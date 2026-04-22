package com.project.password.manager.model.payload;

import org.jetbrains.annotations.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.password.manager.model.IEntry;
import com.project.password.manager.model.ILogin;
import com.project.password.manager.model.ITag;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Entry implements IEntry {

	private String id;
	private String title;
	private Login login;
	private Tag tag;

	public Entry() {
		// for jackson
	}

	public Entry(@NotNull String id, @NotNull String title, @NotNull Login login, @NotNull Tag tag) {
		this.id = id;
		this.title = title;
		this.login = login;
		this.tag = tag;
	}

	@Override
	@NotNull
	public String getId() {
		return id;
	}

	public void setId(@NotNull String id) {
		this.id = id;
	}

	@Override
	@NotNull
	public String getTitle() {
		return title;
	}

	public void setTitle(@NotNull String title) {
		this.title = title;
	}

	@Override
	@NotNull
	public ILogin getLogin() {
		return login;
	}

	public void setLogin(@NotNull Login login) {
		this.login = login;
	}

	@Override
	@NotNull
	public ITag getTag() {
		return tag;
	}

	public void setTag(@NotNull Tag tag) {
		this.tag = tag;
	}

}
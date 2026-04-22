package com.project.password.manager.model.payload;

import org.jetbrains.annotations.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.password.manager.model.INote;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Note implements INote {

	private String id;
	private String description;

	public Note() {
		// for jackson
	}

	public Note(@NotNull String id, @NotNull String description) {
		this.id = id;
		this.description = description;
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
	public String getDescription() {
		return description;
	}

	public void setDescription(@NotNull String description) {
		this.description = description;
	}

}
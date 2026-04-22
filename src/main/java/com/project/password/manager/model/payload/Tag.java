package com.project.password.manager.model.payload;

import org.jetbrains.annotations.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.password.manager.model.ITag;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Tag implements ITag {

	private String id;
	private String value;

	public Tag() {
		// for jackson
	}

	public Tag(@NotNull String id, @NotNull String value) {
		this.id = id;
		this.value = value;
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
	public String getValue() {
		return value;
	}

	public void setValue(@NotNull String value) {
		this.value = value;
	}

}
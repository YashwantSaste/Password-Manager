package com.project.password.manager.model.entry;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class NoteValue {

	@NotBlank
	@Size(max = 4000)
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
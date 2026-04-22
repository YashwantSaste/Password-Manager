package com.project.password.manager.model.entry;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TagValue {

	@NotBlank
	@Size(max = 100)
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
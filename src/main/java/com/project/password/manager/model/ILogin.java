package com.project.password.manager.model;

import java.util.List;

import jakarta.validation.constraints.NotNull;

public interface ILogin {
	@NotNull
	String getLoginName();

	@NotNull
	String getUri();

	@NotNull
	String getEncryptedValue();

	@NotNull
	List<INote> getNotes();

}

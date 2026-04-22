package com.project.password.manager.model.payload;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.password.manager.model.ILogin;
import com.project.password.manager.model.INote;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Login implements ILogin {

	private String loginName;
	private String uri;
	private String encryptedValue;
	private List<Note> notes = new ArrayList<>();

	public Login() {
		// for jackson
	}

	public Login(@NotNull String loginName, @NotNull String uri, @NotNull String encryptedValue,
			@NotNull List<Note> notes) {
		this.loginName = loginName;
		this.uri = uri;
		this.encryptedValue = encryptedValue;
		this.notes = notes;
	}

	@Override
	@NotNull
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(@NotNull String loginName) {
		this.loginName = loginName;
	}

	@Override
	@NotNull
	public String getUri() {
		return uri;
	}

	public void setUri(@NotNull String uri) {
		this.uri = uri;
	}

	@Override
	@NotNull
	public String getEncryptedValue() {
		return encryptedValue;
	}

	public void setEncryptedValue(@NotNull String encryptedValue) {
		this.encryptedValue = encryptedValue;
	}

	@Override
	@NotNull
	public List<INote> getNotes() {
		return new ArrayList<>(notes);
	}

	@NotNull
	public List<Note> getMutableNotes() {
		if (notes == null) {
			notes = new ArrayList<>();
		}
		return notes;
	}

	public void setNotes(@NotNull List<Note> notes) {
		this.notes = notes;
	}

}
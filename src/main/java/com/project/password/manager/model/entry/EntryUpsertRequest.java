package com.project.password.manager.model.entry;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class EntryUpsertRequest {

	@NotBlank
	@Size(max = 200)
	private String label;

	@NotBlank
	@Size(min = 1, max = 4096)
	private String password;

	@Size(max = 200)
	private String username;

	@Size(max = 200)
	private String loginName;

	@Size(max = 2048)
	private String url;

	@Valid
	private List<NoteValue> notes = new ArrayList<>();

	@Valid
	private List<TagValue> tags = new ArrayList<>();

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<NoteValue> getNotes() {
		return notes;
	}

	public void setNotes(List<NoteValue> notes) {
		this.notes = notes;
	}

	public List<TagValue> getTags() {
		return tags;
	}

	public void setTags(List<TagValue> tags) {
		this.tags = tags;
	}

}
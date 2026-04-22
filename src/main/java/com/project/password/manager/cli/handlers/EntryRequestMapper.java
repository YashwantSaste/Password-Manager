package com.project.password.manager.cli.handlers;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.project.password.manager.model.entry.EntryUpsertRequest;
import com.project.password.manager.model.entry.NoteValue;
import com.project.password.manager.model.entry.TagValue;

public final class EntryRequestMapper {

	private EntryRequestMapper() {
	}

	@NotNull
	public static EntryUpsertRequest from(String label, String password, String username, String loginName,
			@Nullable String url, @NotNull List<String> tagValues, @NotNull List<String> noteValues) {
		EntryUpsertRequest request = new EntryUpsertRequest();
		request.setLabel(label);
		request.setPassword(password);
		request.setUsername(username);
		request.setLoginName(loginName);
		request.setUrl(url);
		request.setTags(toTags(tagValues));
		request.setNotes(toNotes(noteValues));
		return request;
	}

	@NotNull
	private static List<TagValue> toTags(@NotNull List<String> tagValues) {
		List<TagValue> tags = new ArrayList<>();
		for (String value : tagValues) {
			TagValue tag = new TagValue();
			tag.setValue(value);
			tags.add(tag);
		}
		return tags;
	}

	@NotNull
	private static List<NoteValue> toNotes(@NotNull List<String> noteValues) {
		List<NoteValue> notes = new ArrayList<>();
		for (String value : noteValues) {
			NoteValue note = new NoteValue();
			note.setDescription(value);
			notes.add(note);
		}
		return notes;
	}
}
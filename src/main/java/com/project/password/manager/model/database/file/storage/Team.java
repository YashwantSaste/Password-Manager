package com.project.password.manager.model.database.file.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jetbrains.annotations.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.password.manager.model.IMetadata;
import com.project.password.manager.model.ITeam;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Team implements ITeam, IFileStorableEntity {

	private String id;
	private String name;
	@JsonIgnore
	private List<String> ownerIds;
	@JsonIgnore
	private List<String> memberIds;
	private IMetadata metadata = new Metadata();
	private String defaultVaultId;
	private String keySalt;

	public Team() {
		// for jackson
	}

	public Team(String id, String name, List<String> owners, List<String> members, String defaultVaultId,
			String keySalt) {
		this.id = id;
		this.name = name;
		this.ownerIds = owners;
		this.memberIds = members;
		this.defaultVaultId = defaultVaultId;
		this.keySalt = keySalt;
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
		this.metadata = metadata;
	}

	@Override
	@NotNull
	public String getId() {
		return id;
	}

	@Override
	@NotNull
	public String name() {
		return name;
	}

	@Override
	@NotNull
	@JsonProperty("owners")
	public List<String> owners() {
		if (ownerIds == null) {
			ownerIds = new ArrayList<>();
		}
		return ownerIds;
	}

	@Override
	@NotNull
	@JsonProperty("members")
	public List<String> memebers() {
		if (memberIds == null) {
			memberIds = new ArrayList<>();
		}
		return memberIds;
	}

	@Override
	@NotNull
	public String getDefaultVaultId() {
		return defaultVaultId != null ? defaultVaultId : "";
	}

	@Override
	@NotNull
	public String getKeySalt() {
		return keySalt != null ? keySalt : "";
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
	public void setOwners(@NotNull List<String> owners) {
		this.ownerIds = new ArrayList<>(owners);
	}

	@Override
	public void setMembers(@NotNull List<String> members) {
		this.memberIds = new ArrayList<>(members);
	}

	@JsonProperty("owners")
	private void setOwnersFromJson(@NotNull List<?> owners) {
		this.ownerIds = normalizeUserIds(owners);
	}

	@JsonProperty("members")
	private void setMembersFromJson(@NotNull List<?> members) {
		this.memberIds = normalizeUserIds(members);
	}

	@Override
	public void setDefaultVaultId(@NotNull String defaultVaultId) {
		this.defaultVaultId = defaultVaultId;
	}

	@Override
	public void setKeySalt(@NotNull String keySalt) {
		this.keySalt = keySalt;
	}

	@Override
	@NotNull
	public String getFileName() {
		return "team.json";
	}

	@NotNull
	private List<String> normalizeUserIds(@NotNull List<?> values) {
		List<String> normalizedIds = new ArrayList<>();
		for (Object value : values) {
			if (value instanceof String stringValue) {
				normalizedIds.add(stringValue);
				continue;
			}
			if (value instanceof Map<?, ?> objectValue) {
				Object idValue = objectValue.get("id");
				if (idValue instanceof String stringId && !stringId.isBlank()) {
					normalizedIds.add(stringId);
				}
			}
		}
		return normalizedIds;
	}
}

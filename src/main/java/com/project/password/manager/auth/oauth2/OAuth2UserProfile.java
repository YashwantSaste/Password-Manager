package com.project.password.manager.auth.oauth2;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.password.manager.util.ModelObjectMapperFactory;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OAuth2UserProfile {

	@JsonProperty("sub")
	private String subject;
	@JsonProperty("preferred_username")
	private String preferredUsername;
	@JsonProperty("email")
	private String email;
	@JsonProperty("name")
	private String name;

	public OAuth2UserProfile() {
		// for Jackson
	}

	@NotNull
	public static OAuth2UserProfile parse(@NotNull String body) {
		try {
			return mapper().readValue(body, OAuth2UserProfile.class);
		} catch (JsonProcessingException exception) {
			throw new RuntimeException("Unable to parse the OAuth2 user profile response.", exception);
		}
	}

	@Nullable
	public String getSubject() {
		return subject;
	}

	@Nullable
	public String getPreferredUsername() {
		return preferredUsername;
	}

	@Nullable
	public String getEmail() {
		return email;
	}

	@Nullable
	public String getName() {
		return name;
	}

	@NotNull
	private static ObjectMapper mapper() {
		return ModelObjectMapperFactory.create();
	}
}
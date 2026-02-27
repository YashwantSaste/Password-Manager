package com.project.password.manager.model.database.file.storage;

import org.jetbrains.annotations.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.password.manager.model.IToken;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Token implements IToken, IFileStorableEntity{

	private String userId;
	private String token;

	public Token() {
		//for jackson
	}

	public Token(@NotNull String userId,@NotNull String token) {
		this.userId=userId;
		this.token=token;
	}

	@Override
	@NotNull
	public String getId() {
		return userId;
	}

	@Override
	@NotNull
	public String getFileName() {
		return "token.json";
	}

	@Override
	@NotNull
	public String getToken() {
		return token;
	}
}

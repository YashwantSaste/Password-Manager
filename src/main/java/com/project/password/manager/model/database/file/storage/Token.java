package com.project.password.manager.model.database.file.storage;

import org.jetbrains.annotations.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.password.manager.model.IToken;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Token implements IToken, IFileStorableEntity{

	private String userId;
	private String token;
	private String tokenType;

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
		return token != null ? token : "";
	}

	@Override
	@NotNull
	public void setToken(@NotNull String token) {
		this.token = token;
	}

	@Override
	@NotNull
	public String getTokenType() {
		return tokenType != null ? tokenType : "";
	}

	@Override
	public void setTokenType(@NotNull String tokenType) {
		this.tokenType = tokenType;
	}

	@Override
	public void setUserId(@NotNull String userId) {
		this.userId = userId;
	}
}

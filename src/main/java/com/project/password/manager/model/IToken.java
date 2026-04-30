package com.project.password.manager.model;

import org.jetbrains.annotations.NotNull;

public interface IToken extends IEntity{

	@NotNull
	String getToken();

	void setToken(@NotNull String token);

	@NotNull
	String getTokenType();

	void setTokenType(@NotNull String tokenType);

	void setUserId(@NotNull String userId);
}

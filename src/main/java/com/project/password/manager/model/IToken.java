package com.project.password.manager.model;

import org.jetbrains.annotations.NotNull;

public interface IToken extends IEntity{

	@NotNull
	String getToken();
}

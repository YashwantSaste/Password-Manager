package com.project.password.manager.service;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.configuration.IOAuth2Configuration;

public class OAuth2LoginService {

	private final IOAuth2Configuration oaAuth2Configuration;

	public OAuth2LoginService(@NotNull IOAuth2Configuration oauAuth2Configuration) {
		this.oaAuth2Configuration = oauAuth2Configuration;
	}

	public void login() {

	}
}

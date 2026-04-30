package com.project.password.manager.model.database.sql;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.model.IToken;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Tokens")
public class JpaToken implements IToken {

	@Id
	@Column(nullable = false, updatable = false)
	private String userId;
	@Column(nullable = false, length = 4096)
	private String token;
	@Column(nullable = false, length = 64)
	private String tokenType;

	protected JpaToken() {
		// for hibernate
	}

	@NotNull
	public String getId() {
		return userId;
	}

	@Override
	@NotNull
	public String getToken() {
		return token != null ? token : "";
	}

	@Override
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
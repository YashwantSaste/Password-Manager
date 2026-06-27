package com.project.password.manager.model.scope;

import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
		@JsonSubTypes.Type(value = UserScope.class, name = "USER"),
		@JsonSubTypes.Type(value = UserGroupScope.class, name = "USER_GROUP"),
		@JsonSubTypes.Type(value = TeamScope.class, name = "TEAM"),
		@JsonSubTypes.Type(value = VaultScope.class, name = "VAULT")
})
public interface IScope {

	@NotNull
	ScopeType getType();
	
	@NotNull
	String getScopeId();
	
}

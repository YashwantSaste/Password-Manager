package com.project.password.manager.configuration.application;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.configuration.ITeamConfiguration;

public class TeamConfiguration implements ITeamConfiguration {

	private final PropertiesReader reader;

	public TeamConfiguration(@NotNull PropertiesReader reader) {
		this.reader = reader;
	}

	@Override
	public boolean onlyAdminCanCreate() {
		return reader.readPropertyAsBoolean(ApplicationProperties.PROPERTY_ALLOW_ONLY_ADMIN_TO_CREATE_TEAM, true);
	}

}

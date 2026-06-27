package com.project.password.manager.configuration;

import jakarta.validation.constraints.NotNull;

public interface IConfiguration {

	@NotNull
	IAppConfiguration appConfiguration();

	@NotNull
	ICLIConfiguration cliConfiguration();

	@NotNull
	IDatabaseConfiguration databaseConfiguration();

	@NotNull
	IArgon2Configuration argon2Configuration();

	@NotNull
	IJwtConfiguration jwtConfiguration();

	@NotNull
	IAESConfiguration aesConfiguration();

	@NotNull
	ISaltKeyConfiguration saltKeyConfiguration();

	@NotNull
	IOAuth2Configuration oauth2Configuration();

	@NotNull
	ITeamConfiguration teamConfiguration();

	@NotNull
	ICacheConfiguration cacheConfiguration();

}

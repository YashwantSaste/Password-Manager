package com.project.password.manager.configuration;

import org.jetbrains.annotations.NotNull;

public interface IAppConfiguration {

	@NotNull
	String name();

	@NotNull
	String version();

	boolean transactionLoggingEnabled();

	@NotNull
	String transactionLogPath();

	boolean includeDatabaseTransactions();

	boolean enableCustomPermissions();

	boolean enableCustomAppRoles();
}

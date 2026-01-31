package com.project.password.manager.database;

import org.jetbrains.annotations.NotNull;

public enum DatabaseVendor {

	POSTGRES("postgres", "org.postgresql.Driver", "jdbc:postgresql://%s:%d/%s"),
	MYSQL("mysql", "com.mysql.cj.jdbc.Driver", "jdbc:mysql://%s:%d/%s");

	@NotNull
	private final String id;
	@NotNull
	private final String driver;
	@NotNull
	private final String jdbcUrlPattern;

	DatabaseVendor(@NotNull String id, @NotNull String driver, @NotNull String jdbcUrlPattern) {
		this.id = id;
		this.driver = driver;
		this.jdbcUrlPattern = jdbcUrlPattern;
	}

	@NotNull
	public static DatabaseVendor from(@NotNull String vendorId) {
		for (DatabaseVendor vendor : values()) {
			if (vendor.id.equalsIgnoreCase(vendorId)) {
				return vendor;
			}
		}
		throw new RuntimeException("Unsupported Database vendor type: " + vendorId);
	}

	@NotNull
	public String driver() {
		return driver;
	}

	@NotNull
	public String jdbcUrl(@NotNull String host, int port, @NotNull String databaseName) {
		return String.format(jdbcUrlPattern, host, port, databaseName);
	}

}

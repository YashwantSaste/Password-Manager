package com.project.password.manager.configuration.application;

public class ApplicationProperties {

	/** Application Configurations **/
	public static final String PROPERTY_APP_NAME = "app.name";
	public static final String PROPERTY_APP_VERSION = "app.version";
	/** Database Configurations **/
	public static final String PROPERTY_DATABASE_ENABLED = "app.database.enabled";
	public static final String PROPERTY_DATABASE_TYPE = "app.database.type";
	public static final String PROPERTY_DATABASE_USERNAME = "app.database.username";
	public static final String PROPERTY_DATABASE_PASSWORD = "app.database.password";
	public static final String PROPERTY_DATABASE_PORT = "app.database.port";
	public static final String PROPERTY_DATABASE_VENDOR = "app.database.vendor";
	public static final String PROPERTY_DATABASE_NAME = "app.database.name";
	public static final String PROPERTY_DATABASE_HOST = "app.database.host";
	public static final String PROPERTY_DATABASE_DDL_MODE = "app.database.ddl.mode";
	/** Argon2 Configurations **/
	public static final String PROPERTY_ARGON2_MEMORY_SIZE = "app.crypto.argon2.memory.kb";
	public static final String PROPERTY_ARGON2_ITERATIONS = "app.crypto.argon2.iterations";
	public static final String PROPERTY_ARGON2_PARALLELISM = "app.crypto.argon2.parallelism";
	public static final String PROPERTY_ARGON2_SALT_LENGTH = "app.crypto.argon2.salt.length";
	public static final String PROPERTY_ARGON2_HASH_LENGTH = "app.crypto.argon2.hash.length";

}

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
	/** JWT Configurations **/
	public static final String PROPERTY_JWT_ALGORITHM = "app.jwt.algorithm";
	public static final String PROPERTY_JWT_ISSUER = "app.jwt.issuer";
	public static final String PROPERTY_JWT_REFRESH_EXPIRATION = "app.jwt.refresh.expiration.ms";
	public static final String PROPERTY_JWT_ACCESS_EXPIRATION = "app.jwt.access.expiration.ms";
	public static final String PROPERTY_JWT_SECRET = "app.jwt.secret";
	public static final String PROPERTY_JWT_PRIVATE_KEY_PATH = "app.jwt.private.key.path";
	public static final String PROPERTY_JWT_PUBLIC_KEY_PATH = "app.jwt.public.key.path";
	/** AES Configurations **/
	public static final String PROPERTY_AES_TRANSFORMATION= "app.aes.transformation";
	public static final String PROPERTY_AES_KEY_SIZE = "app.aes.key.size";
	public static final String PROPERTY_AES_TAG_LENGTH = "app.aes.tag.length";
	public static final String PROPERTY_AES_IV_LENGTH = "app.aes.iv.length";
	/** Salt key generation Configurations */
	public static final String PROPERTY_SALT_KEY_ITERATIONS= "app.salt.iterations";
	public static final String PROPERTY_SALT_KEY_LENGTH = "app.salt.key.size";
	public static final String PROPERTY_SALT_LENGTH = "app.salt.size";
}

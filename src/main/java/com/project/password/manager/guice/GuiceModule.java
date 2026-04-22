package com.project.password.manager.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.matcher.Matchers;
import com.project.password.manager.configuration.IConfiguration;
import com.project.password.manager.configuration.IDatabaseConfiguration;
import com.project.password.manager.configuration.application.Configuration;
import com.project.password.manager.argon.Argon2Encoder;
import com.project.password.manager.cli.runtime.CliOutput;
import com.project.password.manager.cli.runtime.CliSession;
import com.project.password.manager.cli.runtime.CommandHandlerInvoker;
import com.project.password.manager.cli.runtime.ConsoleCliOutput;
import com.project.password.manager.database.DataRepository;
import com.project.password.manager.database.DataRepositoryFactory;
import com.project.password.manager.database.EntryDataRepository;
import com.project.password.manager.encryption.AesGcmEncryptionService;
import com.project.password.manager.encryption.IEncryptionService;
import com.project.password.manager.middleware.RequireAuthorization;
import com.project.password.manager.middleware.TokenAuthorizationInterceptor;
import com.project.password.manager.model.IMetadata;
import com.project.password.manager.model.IToken;
import com.project.password.manager.model.IUser;
import com.project.password.manager.model.IVault;
import com.project.password.manager.service.AuthService;
import com.project.password.manager.service.EntryService;
import com.project.password.manager.service.TokenService;
import com.project.password.manager.service.UserService;
import com.project.password.manager.service.VaultService;
import com.project.password.manager.model.database.file.storage.Metadata;
import com.project.password.manager.model.database.file.storage.Token;
import com.project.password.manager.model.database.file.storage.User;
import com.project.password.manager.model.database.file.storage.Vault;
import com.project.password.manager.model.database.nosql.UserDocument;
import com.project.password.manager.model.database.sql.JpaUser;

public class GuiceModule extends AbstractModule {

	@Override
	protected void configure() {
		IConfiguration configuration = Configuration.getInstance();
		TokenAuthorizationInterceptor authorizationInterceptor = new TokenAuthorizationInterceptor();
		bind(IConfiguration.class).toInstance(configuration);
		bind(CliSession.class).in(Singleton.class);
		bind(CliOutput.class).to(ConsoleCliOutput.class).in(Singleton.class);
		requestInjection(authorizationInterceptor);
		bindInterceptor(Matchers.any(), Matchers.annotatedWith(RequireAuthorization.class), authorizationInterceptor);
		if (!configuration.databaseConfiguration().databaseEnabled()) {
			bind(IUser.class).to(User.class);
			bind(IVault.class).to(Vault.class);
			bind(IMetadata.class).to(Metadata.class);
			bind(IToken.class).to(Token.class);
		} else {
			switch (configuration.databaseConfiguration().type()) {
			case IDatabaseConfiguration.DATABASE_TYPE_SQL: {
				bind(IUser.class).to(JpaUser.class);
			}
			case IDatabaseConfiguration.DATABASE_TYPE_NO_SQL: {
				bind(IUser.class).to(UserDocument.class);
			}
			default:
				throw new UnsupportedOperationException("Could not bind entities: Error: Unsupported Database Type");
			}
		}

	}

	@Provides
	@Singleton
	Argon2Encoder provideArgon2Encoder(IConfiguration configuration) {
		return new Argon2Encoder(configuration.argon2Configuration());
	}

	@Provides
	@Singleton
	DataRepository<IUser, String> provideUserRepository(IConfiguration configuration) {
		return new DataRepositoryFactory(configuration.databaseConfiguration()).getRepository(IUser.class, String.class);
	}

	@Provides
	@Singleton
	DataRepository<IToken, String> provideTokenRepository(IConfiguration configuration) {
		return new DataRepositoryFactory(configuration.databaseConfiguration()).getRepository(IToken.class, String.class);
	}

	@Provides
	@Singleton
	DataRepository<IVault, String> provideVaultRepository(IConfiguration configuration) {
		return new DataRepositoryFactory(configuration.databaseConfiguration()).getRepository(IVault.class, String.class);
	}

	@Provides
	@Singleton
	EntryDataRepository provideEntryRepository(IConfiguration configuration) {
		return new DataRepositoryFactory(configuration.databaseConfiguration()).getEntryRepository();
	}

	@Provides
	@Singleton
	UserService provideUserService(DataRepository<IUser, String> userRepository) {
		return new UserService(userRepository);
	}

	@Provides
	@Singleton
	TokenService provideTokenService(IConfiguration configuration) {
		return new TokenService(configuration.jwtConfiguration());
	}

	@Provides
	@Singleton
	IEncryptionService provideEncryptionService(UserService userService) {
		return new AesGcmEncryptionService(userService);
	}

	@Provides
	@Singleton
	VaultService provideVaultService(DataRepository<IUser, String> userRepository,
			DataRepository<IVault, String> vaultRepository, IEncryptionService encryptionService) {
		return new VaultService(userRepository, vaultRepository, encryptionService,
				com.project.password.manager.util.ModelObjectMapperFactory.create());
	}

	@Provides
	@Singleton
	EntryService provideEntryService(EntryDataRepository entryRepository, DataRepository<IVault, String> vaultRepository,
			IEncryptionService encryptionService) {
		return new EntryService(entryRepository, vaultRepository, encryptionService);
	}

	@Provides
	@Singleton
	AuthService provideAuthService(TokenService tokenService, UserService userService, Argon2Encoder argon2Encoder,
			VaultService vaultService) {
		return new AuthService(tokenService, userService, argon2Encoder, vaultService);
	}

	@Provides
	@Singleton
	CommandHandlerInvoker provideCommandHandlerInvoker() {
		return new CommandHandlerInvoker();
	}
}

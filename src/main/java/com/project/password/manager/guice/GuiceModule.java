package com.project.password.manager.guice;

import com.google.inject.AbstractModule;
import com.project.password.manager.configuration.IConfiguration;
import com.project.password.manager.configuration.IDatabaseConfiguration;
import com.project.password.manager.configuration.application.Configuration;
import com.project.password.manager.model.IMetadata;
import com.project.password.manager.model.IToken;
import com.project.password.manager.model.IUser;
import com.project.password.manager.model.IVault;
import com.project.password.manager.model.database.file.storage.Metadata;
import com.project.password.manager.model.database.file.storage.Token;
import com.project.password.manager.model.database.file.storage.User;
import com.project.password.manager.model.database.file.storage.Vault;
import com.project.password.manager.model.database.nosql.UserDocument;
import com.project.password.manager.model.database.sql.JpaUser;

public class GuiceModule extends AbstractModule {

	@Override
	protected void configure() {
		IConfiguration configuration = new Configuration();
		bind(IConfiguration.class).to(Configuration.class).asEagerSingleton();
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
}

package com.project.password.manager.database;

import java.io.File;

import org.hibernate.SessionFactory;
import org.jetbrains.annotations.NotNull;

import com.project.password.manager.configuration.IConfiguration;
import com.project.password.manager.configuration.IDatabaseConfiguration;
import com.project.password.manager.configuration.application.Workspace;
import com.project.password.manager.database.file.storage.FileEntryRepository;
import com.project.password.manager.database.file.storage.TeamRepository;
import com.project.password.manager.database.file.storage.TokenRepository;
import com.project.password.manager.database.file.storage.UserRepository;
import com.project.password.manager.database.file.storage.VaultRepository;
import com.project.password.manager.database.postgres.hibernate.HibernateBootStrap;
import com.project.password.manager.database.postgres.hibernate.HibernateEntityProvider;
import com.project.password.manager.database.postgres.hibernate.HibernateEntryRepository;
import com.project.password.manager.database.postgres.hibernate.HibernateRepository;
import com.project.password.manager.logging.ITransactionLogger;
import com.project.password.manager.logging.WorkspaceTransactionLogger;
import com.project.password.manager.model.IEntity;
import com.project.password.manager.model.ITeam;
import com.project.password.manager.model.IToken;
import com.project.password.manager.model.IUser;
import com.project.password.manager.model.IVault;
import com.project.password.manager.model.database.sql.JpaToken;
import com.project.password.manager.model.database.sql.JpaEncryptedEntryRecord;
import com.project.password.manager.model.database.sql.JpaUser;
import com.project.password.manager.model.database.sql.JpaVault;
import com.project.password.manager.model.entry.EncryptedEntryRecord;
import com.project.password.manager.util.Logger;

public class DataRepositoryFactory {

	private static final Logger log = Logger.getLogger(DataRepositoryFactory.class);
	@NotNull
	private final IDatabaseConfiguration databaseConfiguration;
	@NotNull
	private final ITransactionLogger transactionLogger;

	public DataRepositoryFactory(@NotNull IConfiguration configuration) {
		this.databaseConfiguration = configuration.databaseConfiguration();
		this.transactionLogger = new WorkspaceTransactionLogger(configuration.appConfiguration(),
				Workspace.getInstance().getRoot());
	}

	@NotNull
	public <T extends IEntity, Id> DataRepository<T, Id> getRepository(@NotNull Class<T> entityClass,
			@NotNull Class<Id> idClass) {
		File workspace = Workspace.getInstance().getRoot();
		if (entityClass.equals(ITeam.class)) {
			@SuppressWarnings("unchecked")
			DataRepository<T, Id> repo = (DataRepository<T, Id>) new TeamRepository(workspace);
			return repo;
		}
		if (databaseConfiguration.databaseEnabled()
				&& IDatabaseConfiguration.DATABASE_TYPE_SQL.equalsIgnoreCase(databaseConfiguration.type())) {
			SessionFactory factory = HibernateBootStrap.init(databaseConfiguration, new HibernateEntityProvider());
			return new HibernateRepository<>(factory, resolveSqlEntityClass(entityClass), transactionLogger);
		}
		log.warn("Database is not enabled hence using local file system as storage");
		if (entityClass.equals(IUser.class)) {
			@SuppressWarnings("unchecked")
			DataRepository<T, Id> repo = (DataRepository<T, Id>) new UserRepository(workspace, transactionLogger);
			return repo;
		}
		if(entityClass.equals(IToken.class)) {
			@SuppressWarnings("unchecked")
			DataRepository<T, Id> repo = (DataRepository<T, Id>) new TokenRepository(workspace, transactionLogger);
			return repo;
		}
		if (entityClass.equals(IVault.class)) {
			@SuppressWarnings("unchecked")
			DataRepository<T, Id> repo = (DataRepository<T, Id>) new VaultRepository(workspace, transactionLogger);
			return repo;
		}
		throw new IllegalArgumentException("No repository available for entity: " + entityClass.getName());
	}

	@NotNull
	public EntryDataRepository getEntryRepository() {
		return createEntryRepository();
	}

	@NotNull
	private EntryDataRepository createEntryRepository() {
		if (databaseConfiguration.databaseEnabled()) {
			if (IDatabaseConfiguration.DATABASE_TYPE_SQL.equalsIgnoreCase(databaseConfiguration.type())) {
				SessionFactory factory = HibernateBootStrap.init(databaseConfiguration, new HibernateEntityProvider());
				return new HibernateEntryRepository(factory, transactionLogger);
			}
			if (IDatabaseConfiguration.DATABASE_TYPE_NO_SQL.equalsIgnoreCase(databaseConfiguration.type())) {
				throw new UnsupportedOperationException("NoSQL entry repository is not implemented yet");
			}
		}
		log.warn("Database is not enabled hence using local file system as storage");
		return new FileEntryRepository(Workspace.getInstance().getRoot(), transactionLogger);
	}

	@NotNull
	@SuppressWarnings("unchecked")
	private <T extends IEntity> Class<T> resolveSqlEntityClass(@NotNull Class<T> entityClass) {
		if (entityClass.equals(IUser.class)) {
			return (Class<T>) JpaUser.class;
		}
		if (entityClass.equals(IToken.class)) {
			return (Class<T>) JpaToken.class;
		}
		if (entityClass.equals(IVault.class)) {
			return (Class<T>) JpaVault.class;
		}
		if (entityClass.equals(EncryptedEntryRecord.class)) {
			return (Class<T>) JpaEncryptedEntryRecord.class;
		}
		return entityClass;
	}
}

package com.project.password.manager.database;

import java.io.File;

import org.hibernate.SessionFactory;
import org.jetbrains.annotations.NotNull;

import com.project.password.manager.configuration.IDatabaseConfiguration;
import com.project.password.manager.configuration.application.Workspace;
import com.project.password.manager.database.file.storage.UserRepository;
import com.project.password.manager.database.postgres.hibernate.HibernateBootStrap;
import com.project.password.manager.database.postgres.hibernate.HibernateEntityProvider;
import com.project.password.manager.database.postgres.hibernate.HibernateRepository;
import com.project.password.manager.model.IEntity;
import com.project.password.manager.model.IUser;
import com.project.password.manager.util.Logger;

public class DataRepositoryFactory {

	private static final Logger log = Logger.getLogger(DataRepositoryFactory.class);
	@NotNull
	private final IDatabaseConfiguration databaseConfiguration;

	public DataRepositoryFactory(@NotNull IDatabaseConfiguration databaseConfiguration) {
		this.databaseConfiguration = databaseConfiguration;
	}

	@NotNull
	public <T extends IEntity, Id> DataRepository<T, Id> getRepository(@NotNull Class<T> entityClass,
			@NotNull Class<Id> idClass) {
		if (databaseConfiguration.databaseEnabled()
				&& IDatabaseConfiguration.DATABASE_TYPE_SQL.equalsIgnoreCase(databaseConfiguration.type())) {
			SessionFactory factory = HibernateBootStrap.init(databaseConfiguration, new HibernateEntityProvider());
			return new HibernateRepository<>(factory, entityClass);
		}
		log.warn("Database is not enabled hence using local file system as storage");
		File workspace = Workspace.getInstance().getRoot();
		if (entityClass.equals(IUser.class)) {
			@SuppressWarnings("unchecked")
			DataRepository<T, Id> repo = (DataRepository<T, Id>) new UserRepository(workspace);
			return repo;
		}
		throw new IllegalArgumentException("No repository available for entity: " + entityClass.getName());
	}
}

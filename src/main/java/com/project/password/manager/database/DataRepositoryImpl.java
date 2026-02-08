package com.project.password.manager.database;

import org.hibernate.SessionFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.project.password.manager.configuration.IDatabaseConfiguration;
import com.project.password.manager.database.postgres.hibernate.HibernateBootStrap;
import com.project.password.manager.database.postgres.hibernate.HibernateEntityProvider;
import com.project.password.manager.database.postgres.hibernate.HibernateRepository;
import com.project.password.manager.model.IEntity;
import com.project.password.manager.util.Logger;

import jakarta.persistence.Id;

public class DataRepositoryImpl implements DataRepository<IEntity, Id> {

	private static final Logger log = Logger.getLogger(DataRepositoryImpl.class);
	@NotNull
	private final IDatabaseConfiguration databaseConfiguration;
	@NotNull
	private final Class<? extends IEntity> clazz;

	public DataRepositoryImpl(@NotNull IDatabaseConfiguration databaseConfiguration,
			@NotNull Class<? extends IEntity> clazz) {
		this.databaseConfiguration = databaseConfiguration;
		this.clazz = clazz;
	}

	@Override
	public void save(@NotNull IEntity entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public @Nullable IEntity findById(@NotNull Id id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(@NotNull IEntity entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(@NotNull Id id, @NotNull IEntity entity) {
		// TODO Auto-generated method stub
	}

	@NotNull
	public DataRepository<? extends IEntity, Id> getEnabledDataRepository() {
		if (databaseConfiguration.databaseEnabled()
				&& databaseConfiguration.type() == IDatabaseConfiguration.DATABASE_TYPE_SQL) {
			SessionFactory factory = HibernateBootStrap.init(databaseConfiguration, new HibernateEntityProvider());
			return new HibernateRepository<IEntity, Id>(factory, clazz);
		}
		log.warn("Database is not enabled hence using local file system as storage");
		// return new FileStorageRepository<IEntity,
		// Id>(Workspace.getInstance().getRoot());
		return null;
	}
}

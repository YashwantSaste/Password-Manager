package com.project.password.manager.database;

import org.hibernate.SessionFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.project.password.manager.configuration.IDatabaseConfiguration;
import com.project.password.manager.database.postgres.hibernate.HibernateBootStrap;
import com.project.password.manager.database.postgres.hibernate.HibernateEntityProvider;
import com.project.password.manager.database.postgres.hibernate.HibernateRepository;
import com.project.password.manager.model.IBase;
import com.project.password.manager.util.Logger;

import jakarta.persistence.Id;

public class DataRepositoryImpl implements DataRepository<IBase, Id> {

	private static final Logger log = Logger.getLogger(DataRepositoryImpl.class);
	@NotNull
	private final IDatabaseConfiguration databaseConfiguration;
	@NotNull
	private final Class<? extends IBase> clazz;

	public DataRepositoryImpl(@NotNull IDatabaseConfiguration databaseConfiguration,
			@NotNull Class<? extends IBase> clazz) {
		this.databaseConfiguration = databaseConfiguration;
		this.clazz = clazz;
		if (databaseConfiguration.databaseEnabled()) {
			log.warn("Database is not enabled hence using local file system as storage");
		}
	}

	@Override
	public void save(@NotNull IBase entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public @Nullable IBase findById(@NotNull Id id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(@NotNull IBase entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(@NotNull IBase id) {
		// TODO Auto-generated method stub
	}

	@NotNull
	private DataRepository<? extends IBase, Id> provideLinkedDataRepository() {
		if (databaseConfiguration.databaseEnabled()
				&& databaseConfiguration.type() == IDatabaseConfiguration.DATABASE_TYPE_SQL) {
			SessionFactory factory = HibernateBootStrap.init(databaseConfiguration, new HibernateEntityProvider());
			return new HibernateRepository<IBase, Id>(factory, clazz);
		}
		return null;
	}
}

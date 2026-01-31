package com.project.password.manager.database.postgres.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.project.password.manager.database.DataRepository;
import com.project.password.manager.model.IBase;

public class Repository<T, Id> implements DataRepository<IBase, Id> {

	@NotNull
	private final SessionFactory sessionFactory;
	@NotNull
	private final Class<? extends IBase> entity;

	public Repository(@NotNull SessionFactory sessionFactory, @NotNull Class<? extends IBase> entity) {
		this.sessionFactory = sessionFactory;
		this.entity = entity;
	}

	@Override
	public void save(@NotNull IBase entity) {
		executeTransaction(session -> session.persist(entity));
	}

	@Override
	@Nullable
	public IBase findById(@NotNull Id id) {
		try (Session session = sessionFactory.openSession()) {
			return session.get(entity, (java.io.Serializable) id);
		}
	}

	@Override
	public void delete(@NotNull IBase entity) {
		executeTransaction(session -> session.remove(entity));
	}

	@Override
	public void update(@NotNull IBase entity) {
		executeTransaction(session -> session.merge(entity));
	}

	private void executeTransaction(@NotNull DatabaseTransaction<T> action) {
		Transaction tx = null;
		try (Session session = sessionFactory.openSession()) {
			tx = session.beginTransaction();
			action.accept(session);
			tx.commit();
		} catch (Exception ex) {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			throw new RuntimeException(ex);
		}
	}

	@FunctionalInterface
	private interface DatabaseTransaction<T> {
		void accept(Session session);
	}

}

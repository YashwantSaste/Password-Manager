package com.project.password.manager.database.postgres.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.project.password.manager.database.DataRepository;
import com.project.password.manager.model.IEntity;

public class HibernateRepository<T extends IEntity, Id> implements DataRepository<IEntity, Id> {

	@NotNull
	private final SessionFactory sessionFactory;
	@NotNull
	private final Class<? extends IEntity> entity;

	public HibernateRepository(@NotNull SessionFactory sessionFactory, @NotNull Class<? extends IEntity> entity) {
		this.sessionFactory = sessionFactory;
		this.entity = entity;
	}

	@Override
	public void save(@NotNull IEntity entity) {
		executeTransaction(session -> session.persist(entity));
	}

	@Override
	@Nullable
	public IEntity findById(@NotNull Id id) {
		try (Session session = sessionFactory.openSession()) {
			return session.get(entity, (java.io.Serializable) id);
		}
	}

	@Override
	public void delete(@NotNull IEntity entity) {
		executeTransaction(session -> session.remove(entity));
	}

	@Override
	public void update(@NotNull IEntity entity) {
		executeTransaction(session -> session.merge(entity));
	}

	private void executeTransaction(DatabaseTransaction<IEntity> action) {
		Transaction tx = null;
		Session session = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			action.accept(session);
			tx.commit();
		} catch (Exception e) {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			throw new RuntimeException(e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}

	@FunctionalInterface
	private interface DatabaseTransaction<T> {
		void accept(@NotNull Session session);
	}
}

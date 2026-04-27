package com.project.password.manager.database.postgres.hibernate;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.project.password.manager.database.DataRepository;
import com.project.password.manager.model.IEntity;
import com.project.password.manager.util.MetadataListener;

public class HibernateRepository<T extends IEntity, Id> implements DataRepository<T, Id> {

	@NotNull
	private final SessionFactory sessionFactory;
	@NotNull
	private final Class<T> entity;

	public HibernateRepository(@NotNull SessionFactory sessionFactory, @NotNull Class<T> entity) {
		this.sessionFactory = sessionFactory;
		this.entity = entity;
	}

	@Override
	public void save(@NotNull T entity) {
		MetadataListener.beforeCreate(entity);
		executeTransaction(session -> session.persist(entity));
	}

	@Override
	@Nullable
	public T findById(@NotNull Id id) {
		try (Session session = sessionFactory.openSession()) {
			return session.get(entity, (java.io.Serializable) id);
		}
	}

	@Override
	@NotNull
	public List<T> findAll() {
		try (Session session = sessionFactory.openSession()) {
			return session.createQuery("from " + entity.getSimpleName(), entity).getResultList();
		}
	}

	@Override
	public void delete(@NotNull Id id) {
		executeTransaction(session -> {
			T persistedEntity = session.get(entity, (java.io.Serializable) id);
			if (persistedEntity != null) {
				session.remove(persistedEntity);
			}
		});
	}

	@Override
	public void update(@NotNull Id id, @NotNull T entity) {
		MetadataListener.beforeUpdate(entity);
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

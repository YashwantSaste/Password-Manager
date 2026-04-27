package com.project.password.manager.database.postgres.hibernate;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.project.password.manager.database.EntryDataRepository;
import com.project.password.manager.database.EntryStorageKey;
import com.project.password.manager.model.database.sql.JpaEncryptedEntryRecord;
import com.project.password.manager.model.entry.EncryptedEntryRecord;

public class HibernateEntryRepository implements EntryDataRepository {

	@NotNull
	private final SessionFactory sessionFactory;

	public HibernateEntryRepository(@NotNull SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void save(@NotNull EncryptedEntryRecord entity) {
		executeTransaction(session -> session.persist(JpaEncryptedEntryRecord.from(entity)));
	}

	@Override
	@Nullable
	public EncryptedEntryRecord findById(@NotNull EntryStorageKey key) {
		try (Session session = sessionFactory.openSession()) {
			JpaEncryptedEntryRecord record = session.createQuery(
					"from JpaEncryptedEntryRecord where vaultId = :vaultId and id = :entryId",
					JpaEncryptedEntryRecord.class)
				.setParameter("vaultId", key.vaultId())
				.setParameter("entryId", key.entryId())
				.uniqueResult();
			return record == null ? null : record.toDomain();
		}
	}

	@Override
	@NotNull
	public List<EncryptedEntryRecord> findAll() {
		try (Session session = sessionFactory.openSession()) {
			return session.createQuery("from JpaEncryptedEntryRecord", JpaEncryptedEntryRecord.class)
				.getResultList()
				.stream()
				.map(JpaEncryptedEntryRecord::toDomain)
				.toList();
		}
	}

	@Override
	@NotNull
	public List<EncryptedEntryRecord> findByVaultId(@NotNull String vaultId) {
		try (Session session = sessionFactory.openSession()) {
			return session.createQuery("from JpaEncryptedEntryRecord where vaultId = :vaultId",
					JpaEncryptedEntryRecord.class)
				.setParameter("vaultId", vaultId)
				.getResultList()
				.stream()
				.map(JpaEncryptedEntryRecord::toDomain)
				.toList();
		}
	}

	@Override
	public void delete(@NotNull EntryStorageKey key) {
		executeTransaction(session -> {
			JpaEncryptedEntryRecord record = session.createQuery(
					"from JpaEncryptedEntryRecord where vaultId = :vaultId and id = :entryId",
					JpaEncryptedEntryRecord.class)
				.setParameter("vaultId", key.vaultId())
				.setParameter("entryId", key.entryId())
				.uniqueResult();
			if (record != null) {
				session.remove(record);
			}
		});
	}

	@Override
	public void update(@NotNull EntryStorageKey key, @NotNull EncryptedEntryRecord entity) {
		executeTransaction(session -> session.merge(JpaEncryptedEntryRecord.from(entity)));
	}

	private void executeTransaction(@NotNull DatabaseTransaction action) {
		Transaction tx = null;
		Session session = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			action.accept(session);
			tx.commit();
		} catch (Exception ex) {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			throw new RuntimeException(ex);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}

	@FunctionalInterface
	private interface DatabaseTransaction {
		void accept(@NotNull Session session);
	}
}
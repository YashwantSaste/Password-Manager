package com.project.password.manager.database.postgres.hibernate;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.jetbrains.annotations.NotNull;

import com.project.password.manager.configuration.IDatabaseConfiguration;
import com.project.password.manager.database.DatabaseVendor;

public final class HibernateBootStrap {

	private static volatile SessionFactory sessionFactory;

	private HibernateBootStrap() {
	}

	public static synchronized SessionFactory init(@NotNull IDatabaseConfiguration databaseConfiguration,
			@NotNull HibernateEntityProvider provider) {

		if (sessionFactory != null) {
			return sessionFactory;
		}
		StandardServiceRegistry registry = null;
		try {
			registry = new StandardServiceRegistryBuilder().applySettings(hibernateConfiguration(databaseConfiguration))
					.build();
			MetadataSources sources = new MetadataSources(registry);
			provider.entities().forEach(sources::addAnnotatedClass);
			sessionFactory = sources.buildMetadata().buildSessionFactory();
			return sessionFactory;
		} catch (Exception ex) {
			if (registry != null) {
				StandardServiceRegistryBuilder.destroy(registry);
			}
			throw new IllegalStateException("Failed to bootstrap Hibernate", ex);
		}
	}

	@NotNull
	public static Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public static void close() {
		sessionFactory.close();
	}

	private static Map<String, Object> hibernateConfiguration(@NotNull IDatabaseConfiguration databaseConfiguration) {
		DatabaseVendor vendor = DatabaseVendor.from(databaseConfiguration.vendor());
		Map<String, Object> config = new HashMap<>();
		config.put(Environment.JAKARTA_JDBC_DRIVER, vendor.driver());
		config.put(Environment.JAKARTA_JDBC_URL, buildJdbcURl(databaseConfiguration, vendor));
		config.put(Environment.JAKARTA_JDBC_USER, databaseConfiguration.username());
		config.put(Environment.JAKARTA_JDBC_PASSWORD, databaseConfiguration.password());
		// config.put(Environment.DIALECT, vendor.dialect());
		config.put(Environment.HBM2DDL_AUTO, databaseConfiguration.ddlMode());
		config.put(Environment.SHOW_SQL, "true");
		config.put(Environment.FORMAT_SQL, "true");
		config.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
		System.out.println(config);
		return config;
	}

	@NotNull
	private static String buildJdbcURl(@NotNull IDatabaseConfiguration databaseConfiguration,
			@NotNull DatabaseVendor vendor) {
		return vendor.jdbcUrl(databaseConfiguration.host(), databaseConfiguration.port(),
				databaseConfiguration.databaseName());
	}
}

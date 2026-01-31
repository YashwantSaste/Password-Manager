package com.project.password.manager;

import java.io.IOException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.project.password.manager.configuration.application.Configuration;
import com.project.password.manager.configuration.application.Workspace;
import com.project.password.manager.database.postgres.hibernate.HibernateBootStrap;
import com.project.password.manager.database.postgres.hibernate.HibernateEntityProvider;
import com.project.password.manager.model.IUser;
import com.project.password.manager.model.database.User;
import com.project.password.manager.util.Logger;

public class Application {

	private static final Logger log = Logger.getLogger(Application.class);

	public static void main(String[] args) throws IOException {
		log.info("App Started");
		Workspace.configureWorkSpace();
		Configuration config = new Configuration();
		System.out.println(config.databaseConfiguration().databaseEnabled());
		SessionFactory factory = HibernateBootStrap.init(config.databaseConfiguration(), new HibernateEntityProvider());
		Session session = factory.getCurrentSession();
		try {
			IUser user = new User(null, "test");
			session.beginTransaction();
			session.persist(user);
			session.getTransaction().commit();
			System.out.println("User saved");
		} catch (Exception ex) {
			if (session != null && session.getTransaction().isActive()) {
				session.getTransaction().rollback();
			}
			throw new RuntimeException("exception occured: " + ex.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
			factory.close();
		}

	}
}

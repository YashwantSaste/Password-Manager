package com.project.password.manager;

import java.io.IOException;

import org.hibernate.SessionFactory;

import com.project.password.manager.configuration.application.Configuration;
import com.project.password.manager.configuration.application.Workspace;
import com.project.password.manager.database.DataRepository;
import com.project.password.manager.database.postgres.hibernate.HibernateBootStrap;
import com.project.password.manager.database.postgres.hibernate.HibernateEntityProvider;
import com.project.password.manager.database.postgres.hibernate.Repository;
import com.project.password.manager.model.IBase;
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
		DataRepository<IBase, Long> sqlRepo = new Repository<IUser, Long>(factory, User.class);
		sqlRepo.save(new User(null, "SQL User"));
	}
}

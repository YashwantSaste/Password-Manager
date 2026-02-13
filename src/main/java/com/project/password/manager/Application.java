package com.project.password.manager;

import java.io.IOException;

import com.project.password.manager.configuration.IDatabaseConfiguration;
import com.project.password.manager.configuration.application.DatabaseConfiguration;
import com.project.password.manager.configuration.application.PropertiesReader;
import com.project.password.manager.database.DataRepository;
import com.project.password.manager.database.DataRepositoryFactory;
import com.project.password.manager.model.IUser;
import com.project.password.manager.model.database.file.storage.User;

public class Application {

	// private static final Logger log = Logger.getLogger(Application.class);

	public static void main(String[] args) throws IOException {
		PropertiesReader reader = PropertiesReader.getInstance();
		IDatabaseConfiguration config = new DatabaseConfiguration(reader);
		DataRepository<IUser, String> repo = new DataRepositoryFactory(config).getRepository(IUser.class, String.class);
		IUser user = new User("dfghj", "dfghjkl", null, null, null, null, null, null);
		repo.save(user);
	}
}

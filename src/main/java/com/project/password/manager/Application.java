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
		IDatabaseConfiguration config = new DatabaseConfiguration(PropertiesReader.getInstance());
		DataRepositoryFactory factory = new DataRepositoryFactory(config);
		DataRepository<User, String> userRepository = factory.getRepository(User.class, String.class);
		User user = new User("some-random-id", "some-fun-username");
		userRepository.save(user);
		IUser user2 = userRepository.findById("some-random-id");
		System.out.println(user2.getName());
		System.out.println("Repository resolved and used successfully");
	}
}

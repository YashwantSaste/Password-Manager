package com.project.password.manager;

import java.io.IOException;

import com.project.password.manager.configuration.IConfiguration;
import com.project.password.manager.configuration.application.Configuration;
import com.project.password.manager.database.DataRepository;
import com.project.password.manager.database.DataRepositoryFactory;
import com.project.password.manager.model.IToken;
import com.project.password.manager.model.IUser;
import com.project.password.manager.model.database.file.storage.Token;
import com.project.password.manager.service.TokenService;

public class Application {

	public static void main(String[] args) throws IOException {
		System.out.println("<=======Start of the Password Manager Application========>");
		IConfiguration configuration = new Configuration();
		DataRepositoryFactory factory = new DataRepositoryFactory(configuration.databaseConfiguration());
		DataRepository<IUser, String> userRepo = factory.getRepository(IUser.class,String.class);
		IUser user = userRepo.findById("some-random-id");
		DataRepository<IToken, String> tokenRepo = factory.getRepository(IToken.class,String.class);
		TokenService service = new TokenService(configuration.jwtConfiguration());
		String token = service.createToken(user);
		System.out.println(token);
		IToken tokenEntity = new Token(user.getId(),token);
		tokenRepo.save(tokenEntity);
		//		IToken tokenRetiral = tokenRepo.findById(user.getId());
		//		System.out.println(tokenRetiral.getToken());
		String tokenFromCache = service.getToken(user);
		System.out.println(tokenFromCache);
	}
}

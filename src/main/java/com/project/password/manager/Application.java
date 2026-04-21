package com.project.password.manager;

import java.io.IOException;

import com.project.password.manager.argon.Argon2Encoder;
import com.project.password.manager.configuration.IConfiguration;
import com.project.password.manager.configuration.application.Configuration;
import com.project.password.manager.database.DataRepositoryFactory;
import com.project.password.manager.model.IUser;
import com.project.password.manager.model.IVault;
import com.project.password.manager.service.AuthService;
import com.project.password.manager.service.TokenService;
import com.project.password.manager.service.UserService;
import com.project.password.manager.service.VaultService;

public class Application {

	public static void main(String[] args) throws IOException {
		System.out.println("<=======Start of the Password Manager Application========>");
		//		Guice.createInjector(new GuiceModule());
		IConfiguration configuration = new Configuration();
		DataRepositoryFactory factory = new DataRepositoryFactory(configuration.databaseConfiguration());
		TokenService tokenService = new TokenService(configuration.jwtConfiguration());

		UserService userService = new UserService(factory.getRepository(IUser.class, String.class));
		VaultService vaultService = new VaultService(factory.getRepository(IUser.class, String.class),
				factory.getRepository(IVault.class, String.class));
		AuthService service = new AuthService(tokenService, userService,
				new Argon2Encoder(configuration.argon2Configuration()), vaultService);
		service.signup("token-user-new", "new password");
		IUser user = userService.getUser("token-user-new");
		System.out.println(user.getAuthVerifier());
		System.out.println(tokenService.getToken(user));
	}
}

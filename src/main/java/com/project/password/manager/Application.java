package com.project.password.manager;

import java.io.IOException;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

public class Application {

	// private static final Logger log = Logger.getLogger(Application.class);

	public static void main(String[] args) throws IOException {
		// log.info("App Started");
//		Workspace.configureWorkSpace();
		String decoded = "some-random-string";
		Argon2PasswordEncoder arg2SpringSecurity = new Argon2PasswordEncoder(16, 32, 1, 30000, 10);
		String hashed = arg2SpringSecurity.encode(decoded);
		System.out.println("Encoded is: " + hashed);
		boolean passmatch = arg2SpringSecurity.matches(decoded, hashed);
		if (passmatch) {
			System.out.println("password matched");
		} else {
			System.out.println("Wrong password");
		}
	}
}

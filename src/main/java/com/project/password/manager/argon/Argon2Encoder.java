package com.project.password.manager.argon;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.configuration.IArgon2Configuration;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import de.mkammerer.argon2.Argon2Factory.Argon2Types;

public class Argon2Encoder {

	@NotNull
	private final IArgon2Configuration configuration;

	public Argon2Encoder(@NotNull IArgon2Configuration configuration, @NotNull String value) {
		this.configuration = configuration;
	}

	@NotNull
	public String getHashValue(@NotNull String normalValue) {
		return argon2().hash(configuration.iterations(), configuration.memoryKb(), configuration.parallelism(),
				normalValue.toCharArray());
	}

	public boolean verify(@NotNull String hashValue, @NotNull String value) {
		return argon2().verify(hashValue, value.toCharArray());
	}

	@NotNull
	private Argon2 argon2() {
		return Argon2Factory.create(Argon2Types.ARGON2i);
	}
}

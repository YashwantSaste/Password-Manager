package com.project.password.manager.configuration;

public interface IArgon2Configuration {

	int memoryKb();

	int iterations();

	int parallelism();

	int saltLengthBytes();

	int hashLengthBytes();
}

package com.project.password.manager.configuration;

public interface ISaltKeyConfiguration {

	int iterations();

	int keyLength();
}

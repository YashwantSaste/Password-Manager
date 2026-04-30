package com.project.password.manager;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.password.manager.database.file.storage.FileManager;
import com.project.password.manager.model.IMetadata;
import com.project.password.manager.model.Status;
import com.project.password.manager.model.UserRole;
import com.project.password.manager.model.database.file.storage.Metadata;
import com.project.password.manager.model.database.file.storage.User;
import com.project.password.manager.model.database.file.storage.Vault;
import com.project.password.manager.util.ModelObjectMapperFactory;

import junit.framework.TestCase;

public class ModelObjectMapperFactoryTest extends TestCase {

	public void testUserSerializationIncludesMetadata() throws Exception {
		ObjectMapper mapper = ModelObjectMapperFactory.create();
		IMetadata metadata = new Metadata();
		LocalDateTime timestamp = LocalDateTime.of(2026, 4, 27, 12, 30, 0);
		metadata.setCreatedAt(timestamp);
		metadata.setUpdatedAt(timestamp);
		metadata.setLastAccessedAt(timestamp);
		metadata.setVersion("1.0");
		metadata.setStatus(Status.ACTIVE);

		User user = new User(
				"user-1",
				"alice",
				"verifier",
				"salt",
				"vault-1",
				List.of(),
				List.of(UserRole.USER),
				metadata);

		String json = mapper.writeValueAsString(user);

		assertTrue(json.contains("\"metadata\""));
		assertTrue(json.contains("\"createdAt\":\"2026-04-27T12:30:00\""));
		assertTrue(json.contains("\"status\":\"active\""));
		assertFalse(json.contains("\"version\":null"));
		assertFalse(json.contains("\"id\":null"));
	}

	public void testFileManagerWritesMetadataToUserJson() throws Exception {
		Path tempFile = Files.createTempFile("user-serialization", ".json");
		try {
			User user = buildUserWithMetadata();
			FileManager<User> fileManager = new FileManager<>(tempFile.toFile(), User.class);

			fileManager.writeToFile(user);
			String json = Files.readString(tempFile);

			assertTrue(json.contains("\"metadata\""));
			assertTrue(json.contains("\"createdAt\":\"2026-04-27T12:30:00\""));
			assertTrue(json.contains("\"status\":\"active\""));
			assertFalse(json.contains("\"version\":null"));
			assertFalse(json.contains("\"id\":null"));
		} finally {
			Files.deleteIfExists(tempFile);
		}
	}

	public void testVaultSerializationIncludesDefaultMetadataObject() throws Exception {
		ObjectMapper mapper = ModelObjectMapperFactory.create();
		Vault vault = new Vault("vault-1", "Default", "user-1", "encrypted");

		String json = mapper.writeValueAsString(vault);

		assertTrue(json.contains("\"metadata\""));
		assertFalse(json.contains("\"metadata\":null"));
		assertFalse(json.contains("\"version\":null"));
		assertFalse(json.contains("\"id\":null"));
	}

	private User buildUserWithMetadata() {
		IMetadata metadata = new Metadata();
		LocalDateTime timestamp = LocalDateTime.of(2026, 4, 27, 12, 30, 0);
		metadata.setCreatedAt(timestamp);
		metadata.setUpdatedAt(timestamp);
		metadata.setLastAccessedAt(timestamp);
		metadata.setVersion("1.0");
		metadata.setStatus(Status.ACTIVE);

		return new User(
				"user-1",
				"alice",
				"verifier",
				"salt",
				"vault-1",
				List.of(),
				List.of(UserRole.USER),
				metadata);
	}
}
package com.project.password.manager.util;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

public class ValidationUtilsTest extends TestCase {

	public void testHasTextReturnsFalseForBlankValues() {
		assertFalse(ValidationUtils.hasText(null));
		assertFalse(ValidationUtils.hasText("   "));
	}

	public void testRequireTextReturnsTrimmedValue() {
		assertEquals("value", ValidationUtils.requireText("  value  ", "missing"));
	}

	public void testRequireTextThrowsForBlankValues() {
		try {
			ValidationUtils.requireText(" ", "missing");
			fail("Expected IllegalArgumentException");
		} catch (IllegalArgumentException exception) {
			assertEquals("missing", exception.getMessage());
		}
	}

	public void testAddIfBlankCollectsMissingLabels() {
		List<String> missingValues = new ArrayList<>();
		ValidationUtils.addIfBlank(null, "app.oauth2.client.id", missingValues);
		ValidationUtils.addIfBlank("configured", "ignored", missingValues);
		assertEquals(List.of("app.oauth2.client.id"), missingValues);
	}
}
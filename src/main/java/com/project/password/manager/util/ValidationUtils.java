package com.project.password.manager.util;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public final class ValidationUtils {

	private static final ValidatorFactory VALIDATOR_FACTORY = Validation.buildDefaultValidatorFactory();
	private static final Validator VALIDATOR = VALIDATOR_FACTORY.getValidator();

	private ValidationUtils() {
	}

	public static <T> void validate(T target) {
		Set<ConstraintViolation<T>> violations = VALIDATOR.validate(target);
		if (!violations.isEmpty()) {
			String message = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(", "));
			throw new ConstraintViolationException(message, violations);
		}
	}

	public static boolean hasText(@Nullable String value) {
		return value != null && !value.isBlank();
	}

	@NotNull
	public static String requireText(@Nullable String value, @NotNull String message) {
		if (!hasText(value)) {
			throw new IllegalArgumentException(message);
		}
		return value.trim();
	}

	public static void addIfBlank(@Nullable String value, @NotNull String label,
			@NotNull Collection<String> missingValues) {
		if (!hasText(value)) {
			missingValues.add(label);
		}
	}

}
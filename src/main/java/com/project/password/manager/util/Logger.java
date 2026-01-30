package com.project.password.manager.util;

import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Logger {
	@NotNull
	private org.apache.logging.log4j.Logger logger;

	private Logger(@NotNull String name) {
		logger = org.apache.logging.log4j.LogManager.getLogger(name);
	}

	private Logger(@NotNull Class<?> clazz) {
		logger = org.apache.logging.log4j.LogManager.getLogger(clazz);
	}

	public static Logger getLogger(@NotNull String client) {
		return new Logger(client);
	}

	public static Logger getLogger(@NotNull Class<?> clazz) {
		return new Logger(clazz);
	}

	public static Logger getLogger(@NotNull Object object) {
		return new Logger(object.getClass());
	}

	@NotNull
	public Level getLevel() {
		return logger.getLevel();
	}

	public void debug(@NotNull Object message) {
		logger.debug(message);
	}

	public void debug(@NotNull Object message, @Nullable Throwable exception) {
		logger.debug(message, exception);
	}

	public void error(@NotNull Object message) {
		logger.error(message);
	}

	public void error(@NotNull Object message, @Nullable Throwable exception) {
		logger.error(message, exception);
	}

	public void fatal(@NotNull Object message) {
		logger.fatal(message);
	}

	public void fatal(@NotNull Object message, @Nullable Throwable exception) {
		logger.fatal(message, exception);
	}

	public void info(@NotNull Object message) {
		logger.info(message);
	}

	public void info(@NotNull Object message, @Nullable Throwable exception) {
		logger.info(message, exception);
	}

	public void trace(@NotNull Object message) {
		logger.trace(message);
	}

	public void trace(@NotNull Object message, @Nullable Throwable exception) {
		logger.trace(message, exception);
	}

	public void warn(@NotNull Object message) {
		logger.warn(message);
	}

	public void warn(@NotNull Object message, @Nullable Throwable exception) {
		logger.warn(message, exception);
	}

	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}

	public boolean isErrorEnabled() {
		return logger.isErrorEnabled();
	}

	public boolean isFatalEnabled() {
		return logger.isFatalEnabled();
	}

	public boolean isInfoEnabled() {
		return logger.isInfoEnabled();
	}

	public boolean isTraceEnabled() {
		return logger.isTraceEnabled();
	}

	public boolean isWarnEnabled() {
		return logger.isWarnEnabled();
	}
}

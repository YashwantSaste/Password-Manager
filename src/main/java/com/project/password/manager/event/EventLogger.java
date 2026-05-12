package com.project.password.manager.event;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.util.Logger;

public class EventLogger {

	private static final Logger log = Logger.getLogger(EventLogger.class);

	public void logReceivedEventAsDebug(@NotNull IEvent event) {
		if (log.isDebugEnabled()) {
			log.debug(event);
		}
	}

	public void logReceivedEventAsInfo(@NotNull IEvent event) {
		log.info(event);
	}

	public void logReceivedEventAsWarn(@NotNull IEvent event) {
		log.warn(event);
	}

	public void logReceivedEventAsError(@NotNull IEvent event) {
		log.error(event);
	}
}
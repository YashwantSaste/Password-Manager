package com.project.password.manager.event.listener;

import com.project.password.manager.event.EventLogger;
import com.project.password.manager.event.IEvent;

public interface IEventListener<T extends IEvent> {

	EventLogger eventLogger = new EventLogger();

	T receivedEvent();

	Object handleEvent();

	void logEvent();

}

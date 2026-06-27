package com.project.password.manager.event;

import jakarta.validation.constraints.NotNull;

public interface IEventPublisher {

	void publish(@NotNull IEvent event);
}
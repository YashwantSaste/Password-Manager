package com.project.password.manager.event.listener;

import jakarta.validation.constraints.NotNull;

import com.project.password.manager.event.IEvent;

public interface IEventListener {

	boolean supports(@NotNull IEvent event);

	void onEvent(@NotNull IEvent event);

}

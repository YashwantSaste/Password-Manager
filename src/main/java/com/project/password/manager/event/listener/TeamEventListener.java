package com.project.password.manager.event.listener;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.event.IEvent;
import com.project.password.manager.model.ITeam;

public class TeamEventListener implements IEventListener {

	@Override
	public boolean supports(@NotNull IEvent event) {
		return event.eventPresentEntity() instanceof ITeam;
	}

	@Override
	public void onEvent(@NotNull IEvent event) {
		// do nothing for now
	}

}

package com.project.password.manager.event.listener;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.cache.UserCache;
import com.project.password.manager.event.IEvent;
import com.project.password.manager.model.IEntity;
import com.project.password.manager.model.IUser;

public class UserEventListener implements IEventListener {

	private final UserCache cache = new UserCache();

	@Override
	public boolean supports(@NotNull IEvent event) {
		return event.eventPresentEntity() instanceof IUser;
	}

	@Override
	public void onEvent(@NotNull IEvent event) {
		if (!supports(event)) {
			throw new IllegalArgumentException("Received event is not a User event");
		}

		switch (event.type()) {
		case CREATE -> cache.addUserToCache(getAfterUser(event));
		case UPDATE -> {
			cache.invalidateUserFromCache(getBeforeUser(event).getId());
			cache.addUserToCache(getAfterUser(event));
		}
		case DELETE -> cache.invalidateUserFromCache(getBeforeUser(event).getId());
		default -> throw new IllegalArgumentException("Unsupported event type: " + event.type());
		}
	}

	@NotNull
	private IUser getBeforeUser(@NotNull IEvent event) {
		IEntity before = event.before();
		if (!(before instanceof IUser user)) {
			throw new IllegalStateException("Expected before entity to be a User for event type: " + event.type());
		}
		return user;
	}

	@NotNull
	private IUser getAfterUser(@NotNull IEvent event) {
		IEntity after = event.after();
		if (!(after instanceof IUser user)) {
			throw new IllegalStateException("Expected after entity to be a User for event type: " + event.type());
		}
		return user;
	}
}
package com.project.password.manager.event;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.model.FieldChange;
import com.project.password.manager.model.IEntity;

public class BaseEvent implements IEvent {

	@NotNull
	private EventType type;
	@NotNull
	private IEntity before;
	@NotNull
	private IEntity after;
	@NotNull
	private List<FieldChange> changes;

	public BaseEvent(@NotNull EventType type, @NotNull IEntity before, @NotNull IEntity after,
			@NotNull List<FieldChange> changes) {
		this.type = type;
		this.before = before;
		this.after = after;
		this.changes = changes;
	}
	@Override
	public EventType type() {
		return type;
	}

	@Override
	public IEntity before() {
		return before;
	}

	@Override
	public IEntity after() {
		return after;
	}

	@Override
	public List<FieldChange> changes() {
		return changes;
	}

	@Override
	public String toString() {
		// TODO: To add proper reading intent for each event
		return super.toString();
	}

}

package com.project.password.manager.event;

import java.util.List;

import com.project.password.manager.model.FieldChange;
import com.project.password.manager.model.IEntity;

public interface IEvent {

	EventType type();

	IEntity before();

	IEntity after();

	List<FieldChange> changes();

	@Override
	String toString();
}

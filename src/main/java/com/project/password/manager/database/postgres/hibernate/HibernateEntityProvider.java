package com.project.password.manager.database.postgres.hibernate;

import java.util.Set;

import com.project.password.manager.model.IBase;
import com.project.password.manager.model.IUser;

public final class HibernateEntityProvider {

	public Set<Class<? extends IBase>> entities() {
		return Set.of(IUser.class);
	}
}

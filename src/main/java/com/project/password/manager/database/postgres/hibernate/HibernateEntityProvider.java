package com.project.password.manager.database.postgres.hibernate;

import java.util.Set;

import com.project.password.manager.model.IEntity;
import com.project.password.manager.model.database.sql.JpaUser;

public final class HibernateEntityProvider {

	public Set<Class<? extends IEntity>> entities() {
		return Set.of(JpaUser.class);
	}
}

package com.project.password.manager.permission;

public interface IBasePermission extends IPermission {

	boolean read();

	boolean delete();

	boolean edit();

	boolean create();
}

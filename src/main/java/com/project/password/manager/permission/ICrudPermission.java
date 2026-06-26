package com.project.password.manager.permission;

public interface ICrudPermission {

	boolean read();

	boolean delete();

	boolean modify();

	boolean create();

	void setRead(boolean read);

	void setDelete(boolean delete);

	void setModify(boolean modify);

	void setCreate(boolean create);
}

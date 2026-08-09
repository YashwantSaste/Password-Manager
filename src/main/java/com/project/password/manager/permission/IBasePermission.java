package com.project.password.manager.permission;

import jakarta.validation.constraints.NotNull;

public interface IBasePermission extends IPermission {

	boolean read();

	boolean delete();

	boolean modify();

	boolean create();

	void setRead(boolean read);

	void setDelete(boolean delete);

	void setModify(boolean modify);

	void setCreate(boolean create);

	@NotNull
	default IBasePermission createBasePermissionForUser() {
		return new BasePermission(true, false, false, false);
	}

	@NotNull
	default IBasePermission createBasePermissionForAdmin() {
		return new BasePermission(true, true, true, true);
	}

}

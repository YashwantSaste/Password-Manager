package com.project.password.manager.database.file.storage;

import java.io.File;

import jakarta.validation.constraints.NotNull;

import com.project.password.manager.logging.ITransactionLogger;
import com.project.password.manager.model.database.file.storage.Token;

public class TokenRepository extends FileStorageRepository<Token, String>{

	private static final String USERS_WORKSPACE_FOLDER = "users";

	public TokenRepository(@NotNull File workspace, @NotNull ITransactionLogger transactionLogger) {
		super(new File(workspace, USERS_WORKSPACE_FOLDER), transactionLogger);
	}

	/*As we are storing the token inside the user folder in the workspace so the
	 * {@code id} here would represent the user id under
	 *  which we are storing the token*/
	@Override
	@NotNull
	protected File resolveEntityDirectoryInFileSystem(@NotNull String id) {
		return new File(workspace, id);
	}

	@Override
	@NotNull
	protected Class<Token> getEntityClass() {
		return Token.class;
	}

	@Override
	@NotNull
	protected String getEntityFileName() {
		return "token.json";
	}

}

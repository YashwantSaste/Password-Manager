package com.project.password.manager.cli.handlers;

import org.jetbrains.annotations.NotNull;

import com.google.inject.Inject;
import com.project.password.manager.cli.commands.EntrySearchCommand;
import com.project.password.manager.cli.runtime.CliOutput;
import com.project.password.manager.cli.runtime.CliSession;
import com.project.password.manager.middleware.RequireAuthorization;
import com.project.password.manager.service.EntryService;
import com.project.password.manager.service.UserService;
import com.project.password.manager.service.VaultService;

public class EntrySearchCommandHandler extends AbstractVaultScopedCommandHandler<EntrySearchCommand.Request> {

	@NotNull
	private final EntryService entryService;

	@Inject
	public EntrySearchCommandHandler(@NotNull CliSession session, @NotNull UserService userService,
			@NotNull VaultService vaultService, @NotNull EntryService entryService, @NotNull CliOutput output) {
		super(session, userService, vaultService, output);
		this.entryService = entryService;
	}

	@Override
	@RequireAuthorization
	public void handle(@NotNull EntrySearchCommand.Request request) {
		output.info(CliViewPrinter.formatEntries(
				entryService.searchEntries(currentUserId(), resolveVaultId(request.getVaultReference()), request.getQuery()),
				request.isShowIds()));
	}
}
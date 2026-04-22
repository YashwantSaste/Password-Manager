package com.project.password.manager.cli.handlers;

import org.jetbrains.annotations.NotNull;

import com.google.inject.Inject;
import com.project.password.manager.cli.commands.EntryGetCommand;
import com.project.password.manager.cli.runtime.CliOutput;
import com.project.password.manager.cli.runtime.CliSession;
import com.project.password.manager.middleware.RequireAuthorization;
import com.project.password.manager.service.EntryService;
import com.project.password.manager.service.UserService;
import com.project.password.manager.service.VaultService;

public class EntryGetCommandHandler extends AbstractVaultScopedCommandHandler<EntryGetCommand.Request> {

	@NotNull
	private final EntryService entryService;

	@Inject
	public EntryGetCommandHandler(@NotNull CliSession session, @NotNull UserService userService,
			@NotNull VaultService vaultService, @NotNull EntryService entryService, @NotNull CliOutput output) {
		super(session, userService, vaultService, output);
		this.entryService = entryService;
	}

	@Override
	@RequireAuthorization
	public void handle(@NotNull EntryGetCommand.Request request) {
		output.info(CliViewPrinter.formatEntry(
				entryService.getEntry(currentUserId(), resolveVaultId(request.getVaultReference()), request.getEntryId())));
	}
}
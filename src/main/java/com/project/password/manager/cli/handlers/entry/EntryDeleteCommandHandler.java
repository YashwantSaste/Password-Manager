package com.project.password.manager.cli.handlers.entry;

import org.jetbrains.annotations.NotNull;

import com.google.inject.Inject;
import com.project.password.manager.cli.commands.entry.EntryDeleteCommand;
import com.project.password.manager.cli.handlers.AbstractVaultScopedCommandHandler;
import com.project.password.manager.cli.runtime.CliOutput;
import com.project.password.manager.cli.runtime.CliSession;
import com.project.password.manager.middleware.RequireAuthorization;
import com.project.password.manager.service.EntryService;
import com.project.password.manager.service.UserService;
import com.project.password.manager.service.VaultService;

public class EntryDeleteCommandHandler extends AbstractVaultScopedCommandHandler<EntryDeleteCommand.Request> {

	@NotNull
	private final EntryService entryService;

	@Inject
	public EntryDeleteCommandHandler(@NotNull CliSession session, @NotNull UserService userService,
			@NotNull VaultService vaultService, @NotNull EntryService entryService, @NotNull CliOutput output) {
		super(session, userService, vaultService, output);
		this.entryService = entryService;
	}

	@Override
	@RequireAuthorization
	public void handle(@NotNull EntryDeleteCommand.Request request) {
		entryService.deleteEntry(currentUserId(), resolveVaultId(request.getVaultReference()), request.getEntryId());
		output.info("Deleted entry " + request.getEntryId());
	}
}
package com.project.password.manager.cli.handlers;

import org.jetbrains.annotations.NotNull;

import com.google.inject.Inject;
import com.project.password.manager.cli.commands.EntryUpdateCommand;
import com.project.password.manager.cli.runtime.CliOutput;
import com.project.password.manager.cli.runtime.CliSession;
import com.project.password.manager.middleware.RequireAuthorization;
import com.project.password.manager.service.EntryService;
import com.project.password.manager.service.UserService;
import com.project.password.manager.service.VaultService;

public class EntryUpdateCommandHandler extends AbstractVaultScopedCommandHandler<EntryUpdateCommand.Request> {

	@NotNull
	private final EntryService entryService;

	@Inject
	public EntryUpdateCommandHandler(@NotNull CliSession session, @NotNull UserService userService,
			@NotNull VaultService vaultService, @NotNull EntryService entryService, @NotNull CliOutput output) {
		super(session, userService, vaultService, output);
		this.entryService = entryService;
	}

	@Override
	@RequireAuthorization
	public void handle(@NotNull EntryUpdateCommand.Request request) {
		output.info(CliViewPrinter.formatEntry(entryService.updateEntry(currentUserId(), resolveVaultId(request.getVaultReference()),
				request.getEntryId(), EntryRequestMapper.from(request.getLabel(), request.getPassword(),
						request.getUsername(), request.getLoginName(), request.getUrl(), request.getTags(),
						request.getNotes()))));
	}
}
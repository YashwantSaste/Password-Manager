package com.project.password.manager.cli.handlers.entry;

import org.jetbrains.annotations.NotNull;

import com.google.inject.Inject;
import com.project.password.manager.cli.commands.entry.EntryCreateCommand;
import com.project.password.manager.cli.handlers.AbstractVaultScopedCommandHandler;
import com.project.password.manager.cli.handlers.CliViewPrinter;
import com.project.password.manager.cli.runtime.CliOutput;
import com.project.password.manager.cli.runtime.CliSession;
import com.project.password.manager.middleware.RequireAuthorization;
import com.project.password.manager.service.EntryService;
import com.project.password.manager.service.UserService;
import com.project.password.manager.service.VaultService;

public class EntryCreateCommandHandler extends AbstractVaultScopedCommandHandler<EntryCreateCommand.Request> {

	@NotNull
	private final EntryService entryService;

	@Inject
	public EntryCreateCommandHandler(@NotNull CliSession session, @NotNull UserService userService,
			@NotNull VaultService vaultService, @NotNull EntryService entryService, @NotNull CliOutput output) {
		super(session, userService, vaultService, output);
		this.entryService = entryService;
	}

	@Override
	@RequireAuthorization
	public void handle(@NotNull EntryCreateCommand.Request request) {
		output.info(CliViewPrinter.formatEntry(entryService.createEntry(currentUserId(), resolveVaultId(request.getVaultReference()),
				EntryRequestMapper.from(request.getLabel(), request.getPassword(), request.getUsername(),
						request.getLoginName(), request.getUrl(), request.getTags(), request.getNotes()))));
	}
}
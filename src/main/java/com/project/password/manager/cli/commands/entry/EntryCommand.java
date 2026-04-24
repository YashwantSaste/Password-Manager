package com.project.password.manager.cli.commands.entry;

import com.project.password.manager.cli.runtime.CliTheme;

import picocli.CommandLine.Command;

@Command(name = "entry", mixinStandardHelpOptions = true, description = "Entry operations.", subcommands = {
		EntryListCommand.class,
		EntryGetCommand.class,
		EntryCreateCommand.class,
		EntryUpdateCommand.class,
		EntryDeleteCommand.class,
		EntrySearchCommand.class,
		picocli.CommandLine.HelpCommand.class })
public class EntryCommand implements Runnable {

	@Override
	public void run() {
		System.out.println(CliTheme.hintPanel("Entry Commands",
				CliTheme.key("use") + CliTheme.muted(" : ") + CliTheme.secondary("list, get, create, update, delete, or search")));
	}
}
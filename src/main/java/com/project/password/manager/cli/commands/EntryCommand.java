package com.project.password.manager.cli.commands;

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
		System.out.println("Use an entry subcommand like list, get, create, update, delete, or search.");
	}
}
package com.project.password.manager.cli.commands;

import picocli.CommandLine.Command;

@Command(name = "password-manager", mixinStandardHelpOptions = true, description = "Interactive password manager CLI.", subcommands = {
		PingCommand.class, TimeCommand.class, EchoCommand.class })
public class BaseCommand implements Runnable {

	@Override
	public void run() {
		System.out.println("Available commands: ping, time, echo. Type --help for usage or exit to quit.");
	}

}

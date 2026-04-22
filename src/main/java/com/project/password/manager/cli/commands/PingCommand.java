package com.project.password.manager.cli.commands;

import picocli.CommandLine.Command;

@Command(name = "ping", description = "Simple connectivity check command.")
public class PingCommand implements Runnable {

	@Override
	public void run() {
		System.out.println("pong");
	}

}
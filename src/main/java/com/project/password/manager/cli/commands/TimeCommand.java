package com.project.password.manager.cli.commands;

import java.time.LocalDateTime;

import picocli.CommandLine.Command;

@Command(name = "time", description = "Prints the current local date and time.")
public class TimeCommand implements Runnable {

	@Override
	public void run() {
		System.out.println(LocalDateTime.now());
	}

}
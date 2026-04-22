package com.project.password.manager.cli.commands;

import java.util.List;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "echo", description = "Prints back the provided text.")
public class EchoCommand implements Runnable {

	@Parameters(arity = "1..*", paramLabel = "TEXT", description = "Words to print.")
	private List<String> text;

	@Override
	public void run() {
		System.out.println(String.join(" ", text));
	}

}
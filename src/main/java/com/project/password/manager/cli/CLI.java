package com.project.password.manager.cli;

import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import com.project.password.manager.cli.commands.BaseCommand;

import picocli.CommandLine;
import picocli.shell.jline3.PicocliJLineCompleter;

public class CLI {

	private static final String CLI_PROMPT_STRING = "password_manager>> ";

	public static void initCLI() {
		try {
			CommandLine commandLine = new CommandLine(new BaseCommand());
			try (Terminal terminal = TerminalBuilder.builder().system(true).build()) {
				LineReader reader = LineReaderBuilder.builder().terminal(terminal)
						.completer(new PicocliJLineCompleter(commandLine.getCommandSpec())).build();
				while (true) {
					try {
						String line = reader.readLine(CLI_PROMPT_STRING);
						if (line == null || line.trim().equalsIgnoreCase("exit")) {
							break;
						}
						if (line.trim().isEmpty()) {
							continue;
						}
						String[] inputArgs = line.trim().split("\\s+");
						commandLine.execute(inputArgs);
					} catch (UserInterruptException e) {
						// Ctrl+C -> ignore and keep the session open.
					} catch (EndOfFileException e) {
						// Ctrl+D -> exit.
						break;
					}
				}
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
}

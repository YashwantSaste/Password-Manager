package com.project.password.manager.cli;

import java.util.ArrayList;
import java.util.List;

import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.ParsedLine;
import org.jline.reader.UserInterruptException;
import org.jline.reader.impl.DefaultParser;
import org.jline.reader.impl.completer.StringsCompleter;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import com.project.password.manager.cli.commands.BaseCommand;
import com.project.password.manager.cli.runtime.CliCommandFactory;
import com.project.password.manager.cli.runtime.CliErrorHandler;
import com.project.password.manager.cli.runtime.CommandHandlerRegistry;
import com.project.password.manager.guice.GuicePlatform;

import picocli.CommandLine;

public class CLI {

	private static final String CLI_PROMPT_STRING = "password_manager>> ";

	public static void initCLI() {
		CommandLine commandLine = createCommandLine();
		try {
			try (Terminal terminal = TerminalBuilder.builder().system(true).build()) {
				List<String> completions = new ArrayList<>(commandLine.getSubcommands().keySet());
				completions.add("exit");
				LineReader reader = LineReaderBuilder.builder().terminal(terminal)
						.parser(new DefaultParser())
						.completer(new StringsCompleter(completions))
						.build();
				while (true) {
					try {
						String line = reader.readLine(CLI_PROMPT_STRING);
						if (line == null || line.trim().equalsIgnoreCase("exit")) {
							break;
						}
						if (line.trim().isEmpty()) {
							continue;
						}
						ParsedLine parsedLine = reader.getParser().parse(line, 0);
						List<String> inputArgs = parsedLine.words();
						if (inputArgs.isEmpty()) {
							continue;
						}
						commandLine.execute(inputArgs.toArray(new String[0]));
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

	private static CommandLine createCommandLine() {
		CommandHandlerRegistry handlerRegistry = GuicePlatform.getInjector().getInstance(CommandHandlerRegistry.class);
		CommandLine commandLine = new CommandLine(new BaseCommand(),
				new CliCommandFactory(GuicePlatform.getInjector(), handlerRegistry));
		commandLine.setExecutionExceptionHandler(new CliErrorHandler());
		return commandLine;
	}
}

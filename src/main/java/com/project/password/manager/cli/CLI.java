package com.project.password.manager.cli;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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
import com.project.password.manager.cli.runtime.CliTheme;
import com.project.password.manager.cli.runtime.CommandHandlerRegistry;
import com.project.password.manager.guice.GuicePlatform;

import picocli.CommandLine;

public class CLI {

	public static void initCLI(String[] args) {
		CommandLine commandLine = createCommandLine();
		if (args != null && args.length > 0) {
			commandLine.execute(args);
			return;
		}
		if (System.console() == null) {
			try {
				runPlainConsole(commandLine);
				return;
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
		}
		try {
			try (Terminal terminal = TerminalBuilder.builder().system(true).build()) {
				if (isDumbTerminal(terminal)) {
					runPlainConsole(commandLine);
					return;
				}
				CliTheme.printStartupExperience(System.out);
				List<String> completions = new ArrayList<>(commandLine.getSubcommands().keySet());
				completions.add("exit");
				LineReader reader = LineReaderBuilder.builder().terminal(terminal)
						.parser(new DefaultParser())
						.completer(new StringsCompleter(completions))
						.build();
				while (true) {
					try {
						String line = reader.readLine(CliTheme.prompt());
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

	private static boolean isDumbTerminal(Terminal terminal) {
		String type = terminal.getType();
		return type == null || type.trim().isEmpty() || type.toLowerCase().contains("dumb");
	}

	private static void runPlainConsole(CommandLine commandLine) throws Exception {
		System.out.println(CliTheme.plainBanner());
		System.out.println(CliTheme.plainMuted("Plain console mode detected. Type help to list commands or exit to close the session."));
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			System.out.print(CliTheme.plainPrompt());
			System.out.flush();
			String line = reader.readLine();
			if (line == null || line.trim().equalsIgnoreCase("exit")) {
				break;
			}
			if (line.trim().isEmpty()) {
				continue;
			}
			commandLine.execute(new DefaultParser().parse(line, 0).words().toArray(new String[0]));
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

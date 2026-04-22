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
import com.project.password.manager.cli.commands.EntryCreateCommand;
import com.project.password.manager.cli.commands.EntryDeleteCommand;
import com.project.password.manager.cli.commands.EntryGetCommand;
import com.project.password.manager.cli.commands.EntryListCommand;
import com.project.password.manager.cli.commands.EntrySearchCommand;
import com.project.password.manager.cli.commands.EntryUpdateCommand;
import com.project.password.manager.cli.commands.LoginCommand;
import com.project.password.manager.cli.commands.LogoutCommand;
import com.project.password.manager.cli.commands.PingCommand;
import com.project.password.manager.cli.commands.SignupCommand;
import com.project.password.manager.cli.commands.VaultCreateCommand;
import com.project.password.manager.cli.commands.VaultDefaultCommand;
import com.project.password.manager.cli.commands.VaultListCommand;
import com.project.password.manager.cli.commands.WhoAmICommand;
import com.project.password.manager.cli.handlers.EntryCreateCommandHandler;
import com.project.password.manager.cli.handlers.EntryDeleteCommandHandler;
import com.project.password.manager.cli.handlers.EntryGetCommandHandler;
import com.project.password.manager.cli.handlers.EntryListCommandHandler;
import com.project.password.manager.cli.handlers.EntrySearchCommandHandler;
import com.project.password.manager.cli.handlers.EntryUpdateCommandHandler;
import com.project.password.manager.cli.handlers.LoginCommandHandler;
import com.project.password.manager.cli.handlers.LogoutCommandHandler;
import com.project.password.manager.cli.handlers.PingCommandHandler;
import com.project.password.manager.cli.handlers.SignupCommandHandler;
import com.project.password.manager.cli.handlers.VaultCreateCommandHandler;
import com.project.password.manager.cli.handlers.VaultDefaultCommandHandler;
import com.project.password.manager.cli.handlers.VaultListCommandHandler;
import com.project.password.manager.cli.handlers.WhoAmICommandHandler;
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
		CommandHandlerRegistry handlerRegistry = new CommandHandlerRegistry()
				.register(LoginCommand.class, LoginCommandHandler.class)
				.register(SignupCommand.class, SignupCommandHandler.class)
				.register(LogoutCommand.class, LogoutCommandHandler.class)
				.register(WhoAmICommand.class, WhoAmICommandHandler.class)
				.register(PingCommand.class, PingCommandHandler.class)
				.register(VaultListCommand.class, VaultListCommandHandler.class)
				.register(VaultCreateCommand.class, VaultCreateCommandHandler.class)
				.register(VaultDefaultCommand.class, VaultDefaultCommandHandler.class)
				.register(EntryListCommand.class, EntryListCommandHandler.class)
				.register(EntryGetCommand.class, EntryGetCommandHandler.class)
				.register(EntryCreateCommand.class, EntryCreateCommandHandler.class)
				.register(EntryUpdateCommand.class, EntryUpdateCommandHandler.class)
				.register(EntryDeleteCommand.class, EntryDeleteCommandHandler.class)
				.register(EntrySearchCommand.class, EntrySearchCommandHandler.class);
		CommandLine commandLine = new CommandLine(new BaseCommand(),
				new CliCommandFactory(GuicePlatform.getInjector(), handlerRegistry));
		commandLine.setExecutionExceptionHandler(new CliErrorHandler());
		return commandLine;
	}
}

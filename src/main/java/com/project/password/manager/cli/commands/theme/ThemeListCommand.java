package com.project.password.manager.cli.commands.theme;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.cli.commands.DelegatingCliCommand;
import com.project.password.manager.cli.handlers.theme.ThemeListCommandHandler;

import picocli.CommandLine.Command;

@Command(name = "list", mixinStandardHelpOptions = true, description = "Lists supported CLI themes.")
public class ThemeListCommand extends DelegatingCliCommand<ThemeListCommand.Request, ThemeListCommandHandler> {

	@Override
	@NotNull
	protected Request buildRequest() {
		return new Request();
	}

	public static final class Request {
		// Marker request.
	}
}
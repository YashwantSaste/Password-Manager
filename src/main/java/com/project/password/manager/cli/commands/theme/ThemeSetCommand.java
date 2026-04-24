package com.project.password.manager.cli.commands.theme;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.cli.commands.DelegatingCliCommand;
import com.project.password.manager.cli.handlers.theme.ThemeSetCommandHandler;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "set", mixinStandardHelpOptions = true, description = "Sets the CLI theme for future runs.")
public class ThemeSetCommand extends DelegatingCliCommand<ThemeSetCommand.Request, ThemeSetCommandHandler> {

	@Parameters(index = "0", description = "Theme name to persist.")
	private String themeName;

	@Override
	@NotNull
	protected Request buildRequest() {
		return new Request(themeName);
	}

	public static final class Request {
		private final String themeName;

		public Request(@NotNull String themeName) {
			this.themeName = themeName;
		}

		@NotNull
		public String getThemeName() {
			return themeName;
		}
	}
}
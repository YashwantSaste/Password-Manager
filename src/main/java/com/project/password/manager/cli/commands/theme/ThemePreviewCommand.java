package com.project.password.manager.cli.commands.theme;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.cli.commands.DelegatingCliCommand;
import com.project.password.manager.cli.handlers.theme.ThemePreviewCommandHandler;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "preview", mixinStandardHelpOptions = true, description = "Previews one theme or all supported themes.")
public class ThemePreviewCommand extends DelegatingCliCommand<ThemePreviewCommand.Request, ThemePreviewCommandHandler> {

	@Parameters(index = "0", arity = "0..1", description = "Optional theme name to preview.")
	private String themeName;

	@Override
	@NotNull
	protected Request buildRequest() {
		return new Request(themeName);
	}

	public static final class Request {
		private final String themeName;

		public Request(String themeName) {
			this.themeName = themeName;
		}

		public String getThemeName() {
			return themeName;
		}
	}
}
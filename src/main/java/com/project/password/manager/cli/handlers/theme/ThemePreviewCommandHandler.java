package com.project.password.manager.cli.handlers.theme;

import org.jetbrains.annotations.NotNull;

import com.google.inject.Inject;
import com.project.password.manager.cli.commands.theme.ThemePreviewCommand;
import com.project.password.manager.cli.handlers.CommandHandler;
import com.project.password.manager.cli.runtime.CliOutput;
import com.project.password.manager.cli.runtime.CliTheme;

public class ThemePreviewCommandHandler implements CommandHandler<ThemePreviewCommand.Request> {

	@NotNull
	private final CliOutput output;

	@Inject
	public ThemePreviewCommandHandler(@NotNull CliOutput output) {
		this.output = output;
	}

	@Override
	public void handle(@NotNull ThemePreviewCommand.Request request) {
		String requestedTheme = request.getThemeName();
		if (requestedTheme == null || requestedTheme.isBlank()) {
			StringBuilder builder = new StringBuilder();
			for (String themeName : CliTheme.supportedThemes()) {
				if (builder.length() > 0) {
					builder.append(System.lineSeparator()).append(System.lineSeparator());
				}
				builder.append(CliTheme.preview(themeName));
			}
			output.info(builder.toString());
			return;
		}

		if (!CliTheme.isSupportedTheme(requestedTheme)) {
			throw new IllegalArgumentException("Unsupported theme: " + requestedTheme);
		}
		output.info(CliTheme.preview(requestedTheme));
	}
}
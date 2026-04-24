package com.project.password.manager.cli.handlers.theme;

import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;

import com.google.inject.Inject;
import com.project.password.manager.cli.commands.theme.ThemeListCommand;
import com.project.password.manager.cli.handlers.CommandHandler;
import com.project.password.manager.cli.runtime.CliOutput;
import com.project.password.manager.cli.runtime.CliTheme;

public class ThemeListCommandHandler implements CommandHandler<ThemeListCommand.Request> {

	@NotNull
	private final CliOutput output;

	@Inject
	public ThemeListCommandHandler(@NotNull CliOutput output) {
		this.output = output;
	}

	@Override
	public void handle(@NotNull ThemeListCommand.Request request) {
		String themes = CliTheme.supportedThemes().stream().map(theme -> {
			String suffix = theme.equals(CliTheme.getActiveThemeName()) ? " (active)" : "";
			return theme + suffix;
		}).collect(Collectors.joining(", "));
		output.info(CliTheme.infoPanel("Available Themes",
				CliTheme.key("themes") + CliTheme.muted(" : ") + CliTheme.secondary(themes),
				CliTheme.key("hint") + CliTheme.muted(" : ") + CliTheme.accent("theme preview <name>")));
	}
}
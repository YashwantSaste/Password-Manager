package com.project.password.manager.cli.commands;

import com.project.password.manager.cli.runtime.CliTheme;

import picocli.CommandLine.Command;

@Command(name = "theme", mixinStandardHelpOptions = true, description = "Theme preview and customization tools.", subcommands = {
		ThemeListCommand.class,
		ThemePreviewCommand.class,
		ThemeSetCommand.class,
		picocli.CommandLine.HelpCommand.class })
public class ThemeCommand implements Runnable {

	@Override
	public void run() {
		System.out.println(CliTheme.hintPanel("Theme Tools",
				CliTheme.key("available") + CliTheme.muted(" : ") + CliTheme.secondary(String.join(", ", CliTheme.supportedThemes())),
				CliTheme.key("preview") + CliTheme.muted(" : ") + CliTheme.accent("theme preview copper-dusk"),
				CliTheme.key("set") + CliTheme.muted(" : ") + CliTheme.accent("theme set paper-retro")));
	}
}
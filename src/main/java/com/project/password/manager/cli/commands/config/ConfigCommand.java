package com.project.password.manager.cli.commands.config;

import com.project.password.manager.cli.runtime.CliTheme;

import picocli.CommandLine.Command;

@Command(name = "config", mixinStandardHelpOptions = true, description = "Inspect and override configuration properties.", subcommands = {
		ConfigListCommand.class,
		ConfigGetCommand.class,
		ConfigSetCommand.class,
		picocli.CommandLine.HelpCommand.class })
public class ConfigCommand implements Runnable {

	@Override
	public void run() {
		System.out.println(CliTheme.hintPanel("Configuration Commands",
				CliTheme.key("list") + CliTheme.muted(" : ") + CliTheme.secondary("config list"),
				CliTheme.key("get") + CliTheme.muted(" : ") + CliTheme.secondary("config get app.cli.theme"),
				CliTheme.key("set") + CliTheme.muted(" : ") + CliTheme.secondary("config set app.cli.theme ocean")));
	}
}
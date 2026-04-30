package com.project.password.manager.cli.commands.auth;

import com.project.password.manager.cli.runtime.CliTheme;

import picocli.CommandLine.Command;

@Command(name = "oauth2", mixinStandardHelpOptions = true, description = "Authenticate through an external OAuth2 provider.", subcommands = {
		OAuth2LoginCommand.class,
		picocli.CommandLine.HelpCommand.class })
public class OAuth2Command implements Runnable {

	@Override
	public void run() {
		System.out.println(CliTheme.hintPanel("OAuth2 Commands",
				CliTheme.key("login") + CliTheme.muted(" : ") + CliTheme.secondary("oauth2 login")));
	}
}
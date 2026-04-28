package com.project.password.manager.cli.handlers.auth;

import org.jetbrains.annotations.NotNull;

import com.google.inject.Inject;
import com.project.password.manager.auth.oauth2.OAuth2VerificationResult;
import com.project.password.manager.cli.commands.auth.OAuth2VerifyCommand;
import com.project.password.manager.cli.handlers.CommandHandler;
import com.project.password.manager.cli.runtime.CliOutput;
import com.project.password.manager.cli.runtime.CliTheme;
import com.project.password.manager.configuration.IOAuth2Configuration;
import com.project.password.manager.service.OAuth2LoginService;

public class OAuth2VerifyCommandHandler implements CommandHandler<OAuth2VerifyCommand.Request> {

	@NotNull
	private final OAuth2LoginService oauth2LoginService;
	@NotNull
	private final CliOutput output;

	@Inject
	public OAuth2VerifyCommandHandler(@NotNull OAuth2LoginService oauth2LoginService, @NotNull CliOutput output) {
		this.oauth2LoginService = oauth2LoginService;
		this.output = output;
	}

	@Override
	public void handle(@NotNull OAuth2VerifyCommand.Request request) {
		OAuth2VerificationResult verificationResult = oauth2LoginService.verifyConfiguration();
		String scopes = verificationResult.getScopes().isEmpty() ? IOAuth2Configuration.DEFAULT_SCOPE
				: String.join(" ", verificationResult.getScopes());
		if (!verificationResult.isValid()) {
			output.info(CliTheme.errorPanel("OAuth2 Configuration Incomplete",
					CliTheme.key("missing") + CliTheme.muted(" : ")
							+ CliTheme.secondary(String.join(", ", verificationResult.getMissingProperties())),
					CliTheme.key("scopes") + CliTheme.muted(" : ") + CliTheme.secondary(scopes)));
			return;
		}
		output.info(CliTheme.successPanel("OAuth2 Configuration Ready",
				CliTheme.key("scopes") + CliTheme.muted(" : ") + CliTheme.secondary(scopes)));
	}
}
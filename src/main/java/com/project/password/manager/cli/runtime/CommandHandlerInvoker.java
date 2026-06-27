package com.project.password.manager.cli.runtime;

import jakarta.validation.constraints.NotNull;

import com.project.password.manager.cli.handlers.CommandHandler;

public class CommandHandlerInvoker {

	public <TRequest> void invoke(@NotNull CommandHandler<TRequest> handler, @NotNull TRequest request) {
		handler.handle(request);
	}
}
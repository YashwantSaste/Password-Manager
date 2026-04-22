package com.project.password.manager.cli.commands;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.cli.handlers.CommandHandler;
import com.project.password.manager.cli.runtime.CommandHandlerInvoker;

public interface RuntimeBoundCommand<H extends CommandHandler<?>> {

	void setHandler(@NotNull H handler);

	void setHandlerInvoker(@NotNull CommandHandlerInvoker handlerInvoker);
}
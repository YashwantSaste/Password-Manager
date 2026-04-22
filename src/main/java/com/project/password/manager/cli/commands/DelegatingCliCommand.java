package com.project.password.manager.cli.commands;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.project.password.manager.cli.handlers.CommandHandler;
import com.project.password.manager.cli.runtime.CommandHandlerInvoker;

public abstract class DelegatingCliCommand<TRequest, THandler extends CommandHandler<TRequest>>
		implements Runnable, RuntimeBoundCommand<THandler> {

	@Nullable
	private THandler handler;
	@Nullable
	private CommandHandlerInvoker handlerInvoker;

	@Override
	public final void run() {
		if (handler == null || handlerInvoker == null) {
			throw new IllegalStateException("Command handler is not configured for " + getClass().getSimpleName());
		}
		handlerInvoker.invoke(handler, buildRequest());
	}

	@Override
	public final void setHandler(@NotNull THandler handler) {
		this.handler = handler;
	}

	@Override
	public final void setHandlerInvoker(@NotNull CommandHandlerInvoker handlerInvoker) {
		this.handlerInvoker = handlerInvoker;
	}

	@NotNull
	protected abstract TRequest buildRequest();
}
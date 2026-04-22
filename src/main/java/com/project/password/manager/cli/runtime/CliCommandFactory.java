package com.project.password.manager.cli.runtime;

import org.jetbrains.annotations.NotNull;

import com.google.inject.Injector;

import picocli.CommandLine;

public class CliCommandFactory implements CommandLine.IFactory {

	@NotNull
	private final CommandLine.IFactory delegate = CommandLine.defaultFactory();
	@NotNull
	private final CommandHandlerBinder handlerBinder;

	public CliCommandFactory(@NotNull Injector injector, @NotNull CommandHandlerRegistry handlerRegistry) {
		this.handlerBinder = new CommandHandlerBinder(injector, handlerRegistry,
				injector.getInstance(CommandHandlerInvoker.class));
	}

	@Override
	public <K> K create(@NotNull Class<K> cls) throws Exception {
		K command = delegate.create(cls);
		handlerBinder.bind(command);
		return command;
	}
}
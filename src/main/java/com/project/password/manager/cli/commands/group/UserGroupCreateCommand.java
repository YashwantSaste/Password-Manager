package com.project.password.manager.cli.commands.group;

import jakarta.validation.constraints.NotNull;

import com.project.password.manager.cli.commands.DelegatingCliCommand;
import com.project.password.manager.cli.handlers.group.UserGroupCreateCommandHandler;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "create", mixinStandardHelpOptions = true, description = "Creates a user group.")
public class UserGroupCreateCommand extends DelegatingCliCommand<UserGroupCreateCommand.Request, UserGroupCreateCommandHandler> {

	@Option(names = "--groupId", required = true, description = "Unique group id.")
	private String groupId;

	@Option(names = "--name", required = true, description = "Group name.")
	private String name;

	@Option(names = "--users", split = ",", description = "Comma-separated user ids.")
	private String[] users;

	@Override
	protected Request buildRequest() {
		return new Request(groupId, name, users == null ? new String[0] : users);
	}

	public static final class Request {
		@NotNull
		private final String groupId;
		@NotNull
		private final String name;
		@NotNull
		private final String[] users;

		public Request(@NotNull String groupId, @NotNull String name, @NotNull String[] users) {
			this.groupId = groupId;
			this.name = name;
			this.users = users;
		}

		@NotNull
		public String getGroupId() {
			return groupId;
		}

		@NotNull
		public String getName() {
			return name;
		}

		@NotNull
		public String[] getUsers() {
			return users;
		}
	}
}

package com.project.password.manager.cli.commands.group;

import jakarta.validation.constraints.NotNull;

import com.project.password.manager.cli.commands.DelegatingCliCommand;
import com.project.password.manager.cli.handlers.group.UserGroupAddUserCommandHandler;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "add-user", mixinStandardHelpOptions = true, description = "Adds a user to a group.")
public class UserGroupAddUserCommand
		extends DelegatingCliCommand<UserGroupAddUserCommand.Request, UserGroupAddUserCommandHandler> {

	@Option(names = "--groupId", required = true, description = "Group id.")
	private String groupId;

	@Option(names = "--userId", required = true, description = "User id.")
	private String userId;

	@Override
	protected Request buildRequest() {
		return new Request(groupId, userId);
	}

	public static final class Request {
		private final String groupId;
		private final String userId;

		public Request(@NotNull String groupId, @NotNull String userId) {
			this.groupId = groupId;
			this.userId = userId;
		}

		@NotNull
		public String getGroupId() {
			return groupId;
		}

		@NotNull
		public String getUserId() {
			return userId;
		}
	}
}

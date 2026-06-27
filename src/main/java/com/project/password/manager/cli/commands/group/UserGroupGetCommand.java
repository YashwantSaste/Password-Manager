package com.project.password.manager.cli.commands.group;

import jakarta.validation.constraints.NotNull;

import com.project.password.manager.cli.commands.DelegatingCliCommand;
import com.project.password.manager.cli.handlers.group.UserGroupGetCommandHandler;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "get", mixinStandardHelpOptions = true, description = "Gets a user group.")
public class UserGroupGetCommand extends DelegatingCliCommand<UserGroupGetCommand.Request, UserGroupGetCommandHandler> {

	@Option(names = "--groupId", required = true, description = "Group id.")
	private String groupId;

	@Override
	protected Request buildRequest() {
		return new Request(groupId);
	}

	public static final class Request {
		@NotNull
		private final String groupId;

		public Request(@NotNull String groupId) {
			this.groupId = groupId;
		}

		@NotNull
		public String getGroupId() {
			return groupId;
		}
	}
}

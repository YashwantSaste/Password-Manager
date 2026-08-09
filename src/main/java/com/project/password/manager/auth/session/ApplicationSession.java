package com.project.password.manager.auth.session;

import com.project.password.manager.model.IToken;

import jakarta.validation.constraints.NotNull;

public record ApplicationSession(@NotNull String userId, @NotNull IToken token) {
}

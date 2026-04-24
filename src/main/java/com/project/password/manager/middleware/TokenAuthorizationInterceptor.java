package com.project.password.manager.middleware;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.google.inject.Inject;

public class TokenAuthorizationInterceptor implements MethodInterceptor {

	@Nullable
	private TokenVerifier tokenVerifier;

	public TokenAuthorizationInterceptor() {
		// Guice injects TokenVerifier via requestInjection in the module configuration.
	}

	@Inject
	public void setTokenVerifier(@NotNull TokenVerifier tokenVerifier) {
		this.tokenVerifier = tokenVerifier;
	}

	@Override
	public Object invoke(@NotNull MethodInvocation invocation) throws Throwable {
		Method method = invocation.getMethod();
		RequireAuthorization authorization = method.getAnnotation(RequireAuthorization.class);
		tokenVerifier.validateCurrentSession(authorization == null ? new com.project.password.manager.model.UserRole[0]
				: authorization.roles());
		return invocation.proceed();
	}
}
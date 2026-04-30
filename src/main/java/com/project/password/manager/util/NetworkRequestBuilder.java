package com.project.password.manager.util;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpRequest.Builder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;
import java.util.StringJoiner;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NetworkRequestBuilder {

	private static final String CONTENT_TYPE_HEADER = "Content-Type";

	@NotNull
	public HttpRequest buildJsonRequest(@NotNull String url, @NotNull String method, @Nullable String body,
			@NotNull String accessToken) {
		Builder httpRequestBuilder = buildBaseRequest(url);
		httpRequestBuilder.method(method, selectBodyPublisher(method, body));
		applyAccessToken(httpRequestBuilder, accessToken);
		if (!isNoBodyMethod(method)) {
			httpRequestBuilder.header(CONTENT_TYPE_HEADER, "application/json");
		}
		return httpRequestBuilder.build();
	}

	@NotNull
	public HttpRequest buildFormRequest(@NotNull String url, @NotNull Map<String, String> formParameters) {
		Builder httpRequestBuilder = buildBaseRequest(url)
				.header(CONTENT_TYPE_HEADER, "application/x-www-form-urlencoded")
				.POST(HttpRequest.BodyPublishers.ofString(buildFormBody(formParameters)));
		return httpRequestBuilder.build();
	}

	@NotNull
	private Builder buildBaseRequest(@NotNull String url) {
		return HttpRequest.newBuilder().uri(URI.create(url)).timeout(Duration.ofSeconds(30));
	}

	@NotNull
	private BodyPublisher selectBodyPublisher(@NotNull String method, @Nullable String body) {
		return isNoBodyMethod(method) ? BodyPublishers.noBody() : BodyPublishers.ofString(body);
	}

	private boolean isNoBodyMethod(@NotNull String method) {
		return method.equalsIgnoreCase(NetworkClient.HTTP_METHOD_GET)
				|| method.equalsIgnoreCase(NetworkClient.HTTP_METHOD_DELETE);
	}

	private void applyAccessToken(@NotNull Builder builder, @NotNull String accessToken) {
		builder.header("Authorization", "Bearer " + accessToken.trim());
	}

	@NotNull
	private String buildFormBody(@NotNull Map<String, String> parameters) {
		StringJoiner joiner = new StringJoiner("&");
		parameters.forEach((key, value) -> {
			if (key != null && value != null) {
				joiner.add(encode(key) + "=" + encode(value));
			}
		});
		return joiner.toString();
	}

	@NotNull
	private String encode(@NotNull String value) {
		return URLEncoder.encode(value, StandardCharsets.UTF_8);
	}
}

package com.project.password.manager.util;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.NotNull;

import com.project.password.manager.exceptions.NetworkClientException;


public class NetworkClient {

	private static final Logger log = Logger.getLogger(NetworkClient.class);
	private static final long HTTP_CLIENT_CONNECTION_TIMEOUT = 10;

	public static final String HTTP_METHOD_GET = "GET";
	public static final String HTTP_METHOD_POST = "POST";
	public static final String HTTP_METHOD_DELETE = "DELETE";

	@NotNull
	private final HttpClient httpClient;

	public NetworkClient() {
		httpClient = getClient();
	}

	@NotNull
	public HttpResponse<String> send(@NotNull HttpRequest request) throws IOException, InterruptedException {
		HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
		log.debug("Request sent to URL: " + request.uri() + " | Response status: " + response.statusCode());
		int status = response.statusCode();
		if (status <= 200 && status > 300) {
			throw new NetworkClientException("Exception occured while making a network request. " + response);
		}
		return response;
	}

	@NotNull
	public CompletableFuture<HttpResponse<String>> sendAsync(@NotNull HttpRequest request) {
		return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenCompose(response -> {
			log.debug("Response received: Status = " + response.statusCode() + " Body = " + response.body());
			int status = response.statusCode();
			if (status >= 200 && status < 300) {
				return CompletableFuture.completedFuture(response);
			}
			log.error("Request failed. Status = " + status + " Body= " + response.body());
			return CompletableFuture
					.failedFuture(new RuntimeException("Error. HTTP Status " + status + ": " + response.body()));
		});
	}

	/*
	 * Protected for tests
	 */
	@NotNull
	public HttpClient getClient() {
		return HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(HTTP_CLIENT_CONNECTION_TIMEOUT))
				.followRedirects(HttpClient.Redirect.NEVER).build();
	}

}


package com.sonar.hipchat.plugin;

import static com.google.common.base.Strings.isNullOrEmpty;

import java.io.IOException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HttpClient, using apache.http.client for posting to hipchat
 * 
 * @author andreas
 *
 */
class HipChatHttpClient {
	private Logger LOGGER = LoggerFactory.getLogger(HipChatHttpClient.class);
	private String postUrl;

	HipChatHttpClient(String postUrl) {
		this.postUrl = postUrl;
	}

	private void sendNotification(String request) {
		HttpPost post = new HttpPost(postUrl);
		post.setEntity(new StringEntity(request, ContentType.APPLICATION_JSON));
		send(post);
	}

	private void send(HttpPost post) {
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		try {
			CloseableHttpResponse response = httpClient.execute(post);
			checkStatus(response);
		}
		catch (IOException e) {
			LOGGER.error("Failed ot send notification", e);
		}
		finally {
			closeClient(httpClient);
		}
	}

	private void checkStatus(CloseableHttpResponse response) {
		int statusCode = response.getStatusLine().getStatusCode();

		if (statusCode / 10 == 20) {
			LOGGER.info("Send successfully notification to HipChat");
		}
		else {
			LOGGER.warn("Failed to notify room: " + statusCode + ". " + response.getStatusLine().getReasonPhrase());
		}
	}

	private void closeClient(CloseableHttpClient httpClient) {
		try {
			httpClient.close();
		}
		catch (IOException e) {
			// thats ok
		}
	}

	/**
	 * Sends a pre message to HipChat in case the pre message is not empty or
	 * null
	 * 
	 * @param preMessage
	 * @param messageBuilder
	 */
	void sendPreNotification(String preMessage, HipChatMessageBuilder messageBuilder) {
		if (!isNullOrEmpty(preMessage)) {
			String message = messageBuilder.getPreNotificationMessage(preMessage);
			sendNotification(message);
		}
	}

	/**
	 * Sends the status message to HipChat
	 * 
	 * @param messageBuilder
	 */
	void sendStatusNotification(HipChatMessageBuilder messageBuilder) {
		String statusMessage = messageBuilder.getStatusMessage();
		if (!isNullOrEmpty(statusMessage)) {
			sendNotification(statusMessage);
		}
	}
}

package com.sonar.hipchat.plugin;

public class SonarHipChatProperties {
	private SonarHipChatProperties() {
		// no instances
	}

	public static final String DISABLED = "sonar.hipchat.disabled";
	public static final String ROOM = "sonar.hipchat.room";
	public static final String TOKEN = "sonar.hipchat.token";
	public static final String URL_TEMPLATE = "https://api.hipchat.com/v2/room/%s/notification?auth_token=%s";
	public static final String PRE_MESSAGE = "sonar.hipchat.message";
}

package com.sonar.hipchat.plugin;

public class SonarHipChatProperties {

	private SonarHipChatProperties() {
		// no instances
	}

	public static final String DISABLED = "sonar.hipchat.disabled";
	public static final String ROOM = "sonar.hipchat.room";
	public static final String TOKEN = "sonar.hipchat.token";
	public static final String URL_TEMPLATE = "sonar.hipchat.url.template";
	public static final String PRE_MESSAGE = "sonar.hipchat.message";
	public static final String MESSAGE_TEMPLATE = "sonar.hipchat.message.template";
}

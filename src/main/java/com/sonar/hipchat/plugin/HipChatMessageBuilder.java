package com.sonar.hipchat.plugin;

/**
 * Builds the messages, that are send to HipChat
 * 
 * @author andreas
 *
 */
interface HipChatMessageBuilder {

	/**
	 * Returns the {@link String} for the pre notification, send to HipChat.
	 * 
	 * @param preMessageTemplate
	 * @return
	 */
	String getPreNotificationMessage(String preMessageTemplate);

	/**
	 * Returns the notification message to HiChat
	 * 
	 * @return
	 */
	String getStatusMessage();
}

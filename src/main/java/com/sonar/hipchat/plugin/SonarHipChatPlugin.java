package com.sonar.hipchat.plugin;

import org.sonar.api.Properties;
import org.sonar.api.Property;
import org.sonar.api.PropertyType;
import org.sonar.api.SonarPlugin;

import java.util.Collections;
import java.util.List;

// @formatter:off
@Properties({ 
	@Property(key = SonarHipChatProperties.DISABLED, 
              name = "Disable hipchat notification",
              defaultValue = "true",
              description = "If set to true no notification is sent to HipChat",
              global = true,
              project = true,
              type = PropertyType.BOOLEAN),
	@Property(key = SonarHipChatProperties.ROOM,
              name = "HipChat room",
              defaultValue = "",
              description = "The HipChat room to send notifications to",
              global = true,
              project = true,
              type = PropertyType.STRING),
	@Property(key = SonarHipChatProperties.TOKEN,
	          name = "Authentication token",
	          defaultValue = "",
	          description = "The authentication token for sending notifications to a HipChat room",
	          global = true,
	          project = true,
	          type = PropertyType.STRING),
	@Property(key = SonarHipChatProperties.PRE_MESSAGE,
			  name = "Notification intro message",
			  description = "Adds a message before the notification. {project} is replaced by project name, {date} by the analysis date",
			  defaultValue = "",
			  global = true,
			  project = true,
			  type = PropertyType.STRING),
	@Property(key = SonarHipChatProperties.MESSAGE_TEMPLATE,
			  name = "Notification message Velocity template",
			  description = "Apache Velocity message template, for list of variables see source code",
			  defaultValue = "$projectName has been analysed at $analysisDate.$newlineStatus: $issuesNew new issues | $issuesResolved resolved issues | $issuesTotal total issues",
			  global = true,
			  project = true,
			  type = PropertyType.STRING),
	@Property(key = SonarHipChatProperties.URL_TEMPLATE,
			  name = "Notification URL template",
			  description = "URL template for notifications",
			  defaultValue = "https://api.hipchat.com/v2/room/%s/notification?auth_token=%s",
			  global = true,
			  project = true,
			  type = PropertyType.STRING),
	})
// @formatter:on
public class SonarHipChatPlugin extends SonarPlugin {
	@SuppressWarnings("rawtypes")
	public List getExtensions() {
		return Collections.singletonList(SonarHipChatNotifier.class);
	}
}

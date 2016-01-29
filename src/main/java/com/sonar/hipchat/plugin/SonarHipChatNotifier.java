package com.sonar.hipchat.plugin;

import static com.sonar.hipchat.plugin.SonarHipChatProperties.DISABLED;
import static com.sonar.hipchat.plugin.SonarHipChatProperties.PRE_MESSAGE;
import static com.sonar.hipchat.plugin.SonarHipChatProperties.ROOM;
import static com.sonar.hipchat.plugin.SonarHipChatProperties.TOKEN;
import static com.sonar.hipchat.plugin.SonarHipChatProperties.URL_TEMPLATE;
import static org.apache.commons.lang.StringUtils.isBlank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.PostJob;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.batch.measure.MetricFinder;
import org.sonar.api.config.Settings;
import org.sonar.api.issue.ProjectIssues;
import org.sonar.api.resources.Project;

public class SonarHipChatNotifier implements PostJob {
	private Logger LOGGER = LoggerFactory.getLogger(SonarHipChatNotifier.class);

	private Settings settings;
	private ProjectIssues projectIssues;

	public SonarHipChatNotifier(Settings settings, ProjectIssues projectIssues, MetricFinder metricFinder) {
		this.settings = settings;
		this.projectIssues = projectIssues;
	}

	@Override
	public void executeOn(Project project, SensorContext context) {
		if (!settings.getBoolean(DISABLED)) {
			String room = settings.getString(ROOM);
			String token = settings.getString(TOKEN);

			if (isBlank(room) || isBlank(token)) {
				LOGGER.warn("No Room or token information available. No notification is send");
				return;
			}

			String postUrl = String.format(URL_TEMPLATE, room, token);
			HipChatHttpClient hipChatHttpClient = new HipChatHttpClient(postUrl);
			HipChatMessageBuilder messageBuilder = new SonarHipChatMessageBuilder(project, projectIssues);
			hipChatHttpClient.sendPreNotification(settings.getString(PRE_MESSAGE), messageBuilder);
			hipChatHttpClient.sendStatusNotification(messageBuilder);
		}
	}
}

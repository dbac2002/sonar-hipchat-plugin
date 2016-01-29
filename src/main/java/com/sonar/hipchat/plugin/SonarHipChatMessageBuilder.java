package com.sonar.hipchat.plugin;

import java.util.List;

import org.sonar.api.issue.Issue;
import org.sonar.api.issue.ProjectIssues;
import org.sonar.api.resources.Project;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.sonar.hipchat.plugin.model.Notification;
import com.sonar.hipchat.plugin.model.Notification.NotificationColor;

final class SonarHipChatMessageBuilder implements HipChatMessageBuilder {
	private final Project project;
	private final ProjectIssues projectIssues;

	SonarHipChatMessageBuilder(Project project, ProjectIssues projectIssues) {
		this.project = project;
		this.projectIssues = projectIssues;
	}

	@Override
	public String getPreNotificationMessage(String preMessageTemplate) {
		String preMessage = preMessageTemplate.replace("{project}", project.getName()).replace("{date}",
				project.getAnalysisDate().toString());
		return createJsonMessage(preMessage, NotificationColor.gray);
	}

	private String createJsonMessage(String message, NotificationColor backgroundColor) {
		Notification notification = new Notification();
		notification.setColor(backgroundColor);
		notification.setMessage(message);
		return new Gson().toJson(notification);
	}

	@Override
	public String getStatusMessage() {
		List<Issue> issues = Lists.newArrayList(projectIssues.issues());
		long newIssues = issues.stream().filter(i -> i.isNew()).count();
		List<Issue> resolvedIssues = Lists.newArrayList(projectIssues.resolvedIssues());

		NotificationColor color = determineNotificationColor(resolvedIssues.size(), newIssues, issues.size());
		String message = String.format(
				"%s has been analysed at %s.%nStatus: %d new issues | %d resolved issues | %d total issues",
				project.getName(), project.getAnalysisDate().toString(), newIssues, resolvedIssues.size(),
				issues.size());

		return createJsonMessage(message, color);
	}

	private NotificationColor determineNotificationColor(int resolved, long newIssues, int totalIssues) {
		if (resolved > 0 && newIssues == 0) {
			return NotificationColor.green;
		}
		else if (newIssues > 0 || totalIssues > 0) {
			return NotificationColor.red;
		}
		return NotificationColor.gray;
	}
}
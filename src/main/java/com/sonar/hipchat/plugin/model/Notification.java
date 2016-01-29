package com.sonar.hipchat.plugin.model;

public class Notification {
	public static enum NotificationColor {
		yellow, red, green, purple, gray, random;
	}
	
	private String message;
	private String message_format = "text";
	private int notification = 0;
	private NotificationColor color;
	
	public void setColor(NotificationColor color) {
		this.color = color;
	}
	
	public NotificationColor getColor() {
		return color;
	}
	
	public void setNotification(int notification) {
		this.notification = notification;
	}
	
	public int getNotification() {
		return notification;
	}

	public String getMessage() {
		return message;
	}

	public String getMessage_format() {
		return message_format;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setMessage_format(String message_format) {
		this.message_format = message_format;
	}
}

package net.rush.gui;

import java.awt.Color;

public class Notification {
	public static Color defaultTitleColor = new Color(40, 210, 210);

	public static Color defaultFirstLineColor = new Color(200, 200, 200);

	public static Color defaultSecondLineColor = new Color(200, 200, 200);

	public static int defaultWidth = 320;

	public static int defaultHeight = 85;

	public static Color defaultBackgroundColor = new Color(30, 30, 30);

	public static Color defaultMouseOverBackgroundColor = new Color(34, 34, 34);
	private String title;
	private String firstLine;
	private String secondLine;
	private Color titleColor;
	private Color firstLineColor;
	private Color secondLineColor;
	private int width;
	private int height;
	private Color backgroundColor;
	private Color mouseOverBackgroundColor;

	public Notification(String firstLine) {
		this.title = "Project Rush";
		this.firstLine = firstLine;
		this.secondLine = "";
		this.titleColor = defaultTitleColor;
		this.firstLineColor = defaultFirstLineColor;
		this.secondLineColor = defaultSecondLineColor;
		this.width = defaultWidth;
		this.height = defaultHeight;
		this.backgroundColor = defaultBackgroundColor;
		this.mouseOverBackgroundColor = defaultMouseOverBackgroundColor;
	}

	public Notification(String title, String firstLine) {
		this.title = title;
		this.firstLine = firstLine;
		this.secondLine = "";
		this.titleColor = defaultTitleColor;
		this.firstLineColor = defaultFirstLineColor;
		this.secondLineColor = defaultSecondLineColor;
		this.width = defaultWidth;
		this.height = defaultHeight;
		this.backgroundColor = defaultBackgroundColor;
		this.mouseOverBackgroundColor = defaultMouseOverBackgroundColor;
	}

	public Notification(String title, String firstLine, String secondLine) {
		this.title = title;
		this.firstLine = firstLine;
		this.secondLine = secondLine;
		this.titleColor = defaultTitleColor;
		this.firstLineColor = defaultFirstLineColor;
		this.secondLineColor = defaultSecondLineColor;
		this.width = defaultWidth;
		this.height = defaultHeight;
		this.backgroundColor = defaultBackgroundColor;
		this.mouseOverBackgroundColor = defaultMouseOverBackgroundColor;
	}

	public Notification(String title, String firstLine, String secondLine,
			Color titleColor) {
		this.title = title;
		this.firstLine = firstLine;
		this.secondLine = secondLine;
		this.titleColor = titleColor;
		this.firstLineColor = defaultFirstLineColor;
		this.secondLineColor = defaultSecondLineColor;
		this.width = defaultWidth;
		this.height = defaultHeight;
		this.backgroundColor = defaultBackgroundColor;
		this.mouseOverBackgroundColor = defaultMouseOverBackgroundColor;
	}
	
	public Notification(String title, String firstLine, String secondLine,
			Color titleColor, Color firstLineColor, Color secondLineColor) {
		this.title = title;
		this.firstLine = firstLine;
		this.secondLine = secondLine;
		this.titleColor = titleColor;
		this.firstLineColor = firstLineColor;
		this.secondLineColor = secondLineColor;
		this.width = defaultWidth;
		this.height = defaultHeight;
		this.backgroundColor = defaultBackgroundColor;
		this.mouseOverBackgroundColor = defaultMouseOverBackgroundColor;
	}

	public Notification(String title, String firstLine, String secondLine,
			Color titleColor, Color firstLineColor, Color secondLineColor,
			int width) {
		this.title = title;
		this.firstLine = firstLine;
		this.secondLine = secondLine;
		this.titleColor = titleColor;
		this.firstLineColor = firstLineColor;
		this.secondLineColor = secondLineColor;
		this.width = width;
		this.height = defaultHeight;
		this.backgroundColor = defaultBackgroundColor;
		this.mouseOverBackgroundColor = defaultMouseOverBackgroundColor;
	}

	public Notification(String title, String firstLine, String secondLine,
			Color titleColor, Color firstLineColor, Color secondLineColor,
			int width, Color backgroundColor, Color mouseOverBackgroundColor) {
		this.title = title;
		this.firstLine = firstLine;
		this.secondLine = secondLine;
		this.titleColor = titleColor;
		this.firstLineColor = firstLineColor;
		this.secondLineColor = secondLineColor;
		this.width = width;
		this.height = defaultHeight;
		this.backgroundColor = defaultBackgroundColor;
		this.mouseOverBackgroundColor = defaultMouseOverBackgroundColor;
	}

	public String getTitle() {
		return this.title;
	}

	public String getFirstLine() {
		return this.firstLine;
	}

	public String getSecondLine() {
		return this.secondLine;
	}

	public Color getTitleColor() {
		return this.titleColor;
	}

	public Color getFirstLineColor() {
		return this.firstLineColor;
	}

	public Color getSecondLineColor() {
		return this.secondLineColor;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public Color getBackgroundColor() {
		return this.backgroundColor;
	}

	public Color getMouseOverBackgroundColor() {
		return this.mouseOverBackgroundColor;
	}

	public void setTitle(String text, Color color) {
		this.title = text;
		this.titleColor = color;
	}

	public void setFirstLine(String text, Color color) {
		this.firstLine = text;
		this.firstLineColor = color;
	}

	public void setSecondLine(String text, Color color) {
		this.secondLine = text;
		this.secondLineColor = color;
	}

	public void setTitle(String text) {
		this.title = text;
	}

	public void setFirstLine(String text) {
		this.firstLine = text;
	}

	public void setSecondLine(String text) {
		this.secondLine = text;
	}

	public void setTitleColor(Color color) {
		this.titleColor = color;
	}

	public void setFirstLineColor(Color color) {
		this.firstLineColor = color;
	}

	public void setSecondLineColor(Color color) {
		this.secondLineColor = color;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setBackgroundColor(Color color) {
		this.backgroundColor = color;
	}

	public void setMouseOverBackgroundColor(Color color) {
		this.mouseOverBackgroundColor = color;
	}

	public boolean show(Notifications not) {
		return not.showNotification(this);
	}
}
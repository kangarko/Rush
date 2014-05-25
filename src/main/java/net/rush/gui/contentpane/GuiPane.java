package net.rush.gui.contentpane;

import java.awt.Color;

public class GuiPane {

	public static Color defaultTitleColor = new Color(40, 210, 210);
	public static Color defaultFirstLineColor = new Color(200, 200, 200);
	public static Color defaultSecondLineColor = new Color(200, 200, 200);
	public static int defaultWidth = 320;
	public static int defaultHeight = 85;
	public static Color defaultBackgroundColor = new Color(30, 30, 30);
	public static Color defaultMouseOverBackgroundColor = new Color(34, 34, 34);

	public String title;
	public String firstLine;
	public String secondLine;
	public Color titleColor;
	public Color firstLineColor;
	public Color secondLineColor;
	public int width;
	public int height;
	public Color backgroundColor;
	public Color mouseOverBackgroundColor;

	public GuiPane(String firstLine) {
		title = "Project Rush";
		this.firstLine = firstLine;
		secondLine = "";
		titleColor = defaultTitleColor;
		firstLineColor = defaultFirstLineColor;
		secondLineColor = defaultSecondLineColor;
		width = defaultWidth;
		height = defaultHeight;
		backgroundColor = defaultBackgroundColor;
		mouseOverBackgroundColor = defaultMouseOverBackgroundColor;
	}

	public GuiPane(String title, String firstLine) {
		this.title = title;
		this.firstLine = firstLine;
		secondLine = "";
		titleColor = defaultTitleColor;
		firstLineColor = defaultFirstLineColor;
		secondLineColor = defaultSecondLineColor;
		width = defaultWidth;
		height = defaultHeight;
		backgroundColor = defaultBackgroundColor;
		mouseOverBackgroundColor = defaultMouseOverBackgroundColor;
	}

	public GuiPane(String title, String firstLine, String secondLine) {
		this.title = title;
		this.firstLine = firstLine;
		this.secondLine = secondLine;
		titleColor = defaultTitleColor;
		firstLineColor = defaultFirstLineColor;
		secondLineColor = defaultSecondLineColor;
		width = defaultWidth;
		height = defaultHeight;
		backgroundColor = defaultBackgroundColor;
		mouseOverBackgroundColor = defaultMouseOverBackgroundColor;
	}

	public GuiPane(String title, String firstLine, String secondLine, Color titleColor) {
		this.title = title;
		this.firstLine = firstLine;
		this.secondLine = secondLine;
		this.titleColor = titleColor;
		firstLineColor = defaultFirstLineColor;
		secondLineColor = defaultSecondLineColor;
		width = defaultWidth;
		height = defaultHeight;
		backgroundColor = defaultBackgroundColor;
		mouseOverBackgroundColor = defaultMouseOverBackgroundColor;
	}

	public GuiPane(String title, String firstLine, String secondLine, Color titleColor, Color firstLineColor, Color secondLineColor) {
		this.title = title;
		this.firstLine = firstLine;
		this.secondLine = secondLine;
		this.titleColor = titleColor;
		this.firstLineColor = firstLineColor;
		this.secondLineColor = secondLineColor;
		width = defaultWidth;
		height = defaultHeight;
		backgroundColor = defaultBackgroundColor;
		mouseOverBackgroundColor = defaultMouseOverBackgroundColor;
	}

	public GuiPane(String title, String firstLine, String secondLine, Color titleColor, Color firstLineColor, Color secondLineColor, int width) {
		this.title = title;
		this.firstLine = firstLine;
		this.secondLine = secondLine;
		this.titleColor = titleColor;
		this.firstLineColor = firstLineColor;
		this.secondLineColor = secondLineColor;
		this.width = width;
		height = defaultHeight;
		backgroundColor = defaultBackgroundColor;
		mouseOverBackgroundColor = defaultMouseOverBackgroundColor;
	}

	public GuiPane(String title, String firstLine, String secondLine, Color titleColor, Color firstLineColor, Color secondLineColor, int width, Color backgroundColor, Color mouseOverBackgroundColor) {
		this.title = title;
		this.firstLine = firstLine;
		this.secondLine = secondLine;
		this.titleColor = titleColor;
		this.firstLineColor = firstLineColor;
		this.secondLineColor = secondLineColor;
		this.width = width;
		height = defaultHeight;
		this.backgroundColor = defaultBackgroundColor;
		this.mouseOverBackgroundColor = defaultMouseOverBackgroundColor;
	}

	public void setTitle(String text, Color color) {
		title = text;
		titleColor = color;
	}

	public void setFirstLine(String text, Color color) {
		firstLine = text;
		firstLineColor = color;
	}

	public void setSecondLine(String text, Color color) {
		secondLine = text;
		secondLineColor = color;
	}

	public void setTitle(String text) {
		title = text;
	}

	public void setFirstLine(String text) {
		firstLine = text;
	}

	public void setSecondLine(String text) {
		secondLine = text;
	}

	public void setTitleColor(Color color) {
		titleColor = color;
	}

	public void setFirstLineColor(Color color) {
		firstLineColor = color;
	}

	public void setSecondLineColor(Color color) {
		secondLineColor = color;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setBackgroundColor(Color color) {
		backgroundColor = color;
	}

	public void setMouseOverBackgroundColor(Color color) {
		mouseOverBackgroundColor = color;
	}
}
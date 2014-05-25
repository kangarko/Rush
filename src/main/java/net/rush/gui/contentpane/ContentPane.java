package net.rush.gui.contentpane;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

class ContentPane extends JPanel {

	private static final long serialVersionUID = 1L;

	private int titleY = 27;
	private int additionY1 = 25;
	private int additionY2 = 15;

	private String title;
	private String firstLine;
	private String secondLine;
	
	private Color titleColor;
	private Color firstLineColor;
	private Color secondLineColor;
	private Color backgroundColor;
	private Color mouseOverBackgroundColor;

	public ContentPane(String title, String firstLine, String secondLine) {
		this.title = title;
		this.firstLine = firstLine;
		this.secondLine = secondLine;
		titleColor = GuiPane.defaultTitleColor;
		firstLineColor = GuiPane.defaultFirstLineColor;
		secondLineColor = GuiPane.defaultSecondLineColor;
		backgroundColor = GuiPane.defaultBackgroundColor;
		mouseOverBackgroundColor = GuiPane.defaultMouseOverBackgroundColor;
		setBackground(backgroundColor);
	}

	public ContentPane(String title, String firstLine, String secondLine, Color titleColor, Color firstLineColor, Color secondLineColor) {
		this.title = title;
		this.firstLine = firstLine;
		this.secondLine = secondLine;
		this.titleColor = titleColor;
		this.firstLineColor = firstLineColor;
		this.secondLineColor = secondLineColor;
		backgroundColor = GuiPane.defaultBackgroundColor;
		mouseOverBackgroundColor = GuiPane.defaultMouseOverBackgroundColor;
		setBackground(backgroundColor);
	}

	public ContentPane(String title, String firstLine, String secondLine, Color titleColor, Color firstLineColor, Color secondLineColor, Color backgroundColor, Color mouseOverBackgroundColor) {
		this.title = title;
		this.firstLine = firstLine;
		this.secondLine = secondLine;
		this.titleColor = titleColor;
		this.firstLineColor = firstLineColor;
		this.secondLineColor = secondLineColor;
		this.backgroundColor = backgroundColor;
		this.mouseOverBackgroundColor = mouseOverBackgroundColor;
		setBackground(backgroundColor);
	}

	public ContentPane(GuiPane pane) {
		title = pane.title;
		firstLine = pane.firstLine;
		secondLine = pane.secondLine;
		titleColor = pane.titleColor;
		firstLineColor = pane.firstLineColor;
		secondLineColor = pane.secondLineColor;
		backgroundColor = pane.backgroundColor;
		mouseOverBackgroundColor = pane.mouseOverBackgroundColor;
		setBackground(backgroundColor);
	}

	public void setBackgroundColor() {
		setBackground(backgroundColor);
	}

	public void setMouseOverBackgroundColor() {
		setBackground(mouseOverBackgroundColor);
	}

	@Override
	public void paint(Graphics g) {
		super.paintComponent(g);
		g.setFont(new Font("Verdana", 1, 16));
		g.setColor(titleColor);

		g.drawString(title, 17, titleY);
		g.setFont(new Font("Verdana", 0, 12));

		g.setColor(firstLineColor);
		g.drawString(firstLine, 17, titleY + additionY1);

		g.setColor(secondLineColor);
		g.drawString(secondLine, 17, titleY + additionY1 + additionY2);
	}

}
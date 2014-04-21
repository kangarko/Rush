package net.rush.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

class ContentPanel extends JPanel {

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

	ContentPanel(String title, String firstLine, String secondLine) {
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

	ContentPanel(String title, String firstLine, String secondLine, Color titleColor, Color firstLineColor, Color secondLineColor) {
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

	ContentPanel(String title, String firstLine, String secondLine, Color titleColor, Color firstLineColor, Color secondLineColor, Color backgroundColor, Color mouseOverBackgroundColor) {
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

	ContentPanel(GuiPane pane) {
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

	void setBackgroundColor() {
		setBackground(backgroundColor);
	}

	void setMouseOverBackgroundColor() {
		setBackground(mouseOverBackgroundColor);
	}

	@Override
	public void paint(Graphics graphics) {
		super.paintComponent(graphics);
		graphics.setFont(new Font("Verdana", 1, 16));
		graphics.setColor(titleColor);

		graphics.drawString(title, 17, titleY);
		graphics.setFont(new Font("Verdana", 0, 12));

		graphics.setColor(firstLineColor);
		graphics.drawString(firstLine, 17, titleY + additionY1);

		graphics.setColor(secondLineColor);
		graphics.drawString(secondLine, 17, titleY + additionY1 + additionY2);
	}

	public static class ContentFrame extends JFrame implements WindowListener {

		private static final long serialVersionUID = 1L;

		ContentFrame(String title) {
			super(title);
		}

		@Override
		public void windowActivated(WindowEvent event) {
		}

		@Override
		public void windowClosed(WindowEvent event) {
		}

		@Override
		public void windowClosing(WindowEvent event) {
		}

		@Override
		public void windowDeactivated(WindowEvent event) {
			toFront();
		}

		@Override
		public void windowDeiconified(WindowEvent event) {
		}

		@Override
		public void windowIconified(WindowEvent event) {
		}

		@Override
		public void windowOpened(WindowEvent event) {
		}
	}
}
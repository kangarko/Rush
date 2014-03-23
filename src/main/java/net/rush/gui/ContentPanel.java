package net.rush.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.google.common.collect.Maps;

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
		this.titleColor = Notification.defaultTitleColor;
		this.firstLineColor = Notification.defaultFirstLineColor;
		this.secondLineColor = Notification.defaultSecondLineColor;
		this.backgroundColor = Notification.defaultBackgroundColor;
		this.mouseOverBackgroundColor = Notification.defaultMouseOverBackgroundColor;
		setBackground(this.backgroundColor);
	}

	ContentPanel(String title, String firstLine, String secondLine, Color titleColor, Color firstLineColor, Color secondLineColor) {
		this.title = title;
		this.firstLine = firstLine;
		this.secondLine = secondLine;
		this.titleColor = titleColor;
		this.firstLineColor = firstLineColor;
		this.secondLineColor = secondLineColor;
		this.backgroundColor = Notification.defaultBackgroundColor;
		this.mouseOverBackgroundColor = Notification.defaultMouseOverBackgroundColor;
		setBackground(this.backgroundColor);
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

	ContentPanel(Notification notification) {
		this.title = notification.getTitle();
		this.firstLine = notification.getFirstLine();
		this.secondLine = notification.getSecondLine();
		this.titleColor = notification.getTitleColor();
		this.firstLineColor = notification.getFirstLineColor();
		this.secondLineColor = notification.getSecondLineColor();
		this.backgroundColor = notification.getBackgroundColor();
		this.mouseOverBackgroundColor = notification.getMouseOverBackgroundColor();
		setBackground(this.backgroundColor);
	}

	void setBackgroundColor() {
		setBackground(this.backgroundColor);
	}

	void setMouseOverBackgroundColor() {
		setBackground(this.mouseOverBackgroundColor);
	}

	public void paint(Graphics g) {
		super.paintComponent(g);
		g.setFont(new Font("Verdana", 1, 16));
		g.setColor(this.titleColor);
		g.drawString(this.title, 17, this.titleY);
		g.setFont(new Font("Verdana", 0, 12));
		g.setColor(this.firstLineColor);
		g.drawString(this.firstLine, 17, this.titleY + this.additionY1);
		g.setColor(this.secondLineColor);
		g.drawString(this.secondLine, 17, this.titleY + this.additionY1 + this.additionY2);
	}
	
	
	public static class ContentFrame extends JFrame implements WindowListener {

		private static final long serialVersionUID = 1L;

		ContentFrame(String title) {
			super(title);
		}

		public void windowActivated(WindowEvent event) {
		}

		public void windowClosed(WindowEvent event) {
		}

		public void windowClosing(WindowEvent event) {
		}

		public void windowDeactivated(WindowEvent event) {
			toFront();
		}

		public void windowDeiconified(WindowEvent event) {
		}

		public void windowIconified(WindowEvent event) {
		}

		public void windowOpened(WindowEvent event) {
		}
	}
	
	public static enum NotificationCorner {

		TOP_LEFT(0), 
		TOP_RIGHT(1), 
		BOTTOM_LEFT(2), 
		BOTTOM_RIGHT(3);

		private final byte data;
		private static final Map<Byte, NotificationCorner> BY_DATA;

		static {
			BY_DATA = Maps.newHashMap();

			for (NotificationCorner corner : values())
				BY_DATA.put(Byte.valueOf(corner.data), corner);
		}

		private NotificationCorner(int data) {
			this.data = ((byte) data);
		}

		byte getData() {
			return this.data;
		}
	}
}
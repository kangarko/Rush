package net.rush.gui.contentpane;

import java.awt.Cursor;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JFrame;
import javax.swing.Timer;

import org.bukkit.ChatColor;

public class RushGui {

	private ContentPaneListener guiListener = new ContentPaneListener(this);;
	private JFrame contentFrame;
	private ContentPane contentPanel;
	private Timer timerFadeIn;
	private Timer timerOpaque;
	private Timer timerFadeOut;
	private float opacity;
	private int ticks;
	private int tickLength = 30;
	private int x;
	private int y;
	private int fadeInTicks = 10;
	private int fadeOutTicks = 10;
	private int opaqueTicks = 50;
	private float maxOpacity = 1.0F;

	private int arcSize = 20;

	private int screenOffsetX = 13;

	private int screenOffsetY = 13;
	private int fadeInMoveSpeedX = 0;
	private int fadeInMoveSpeedY = -1;
	private int fadeOutMoveSpeedX = 0;
	private int fadeOutMoveSpeedY = -1;

	public RushGui() {
		GuiPane pane = new GuiPane("GUI successfully started!");
		showPane(pane);
	}

	JFrame getContentFrame() {
		return contentFrame;
	}

	ContentPane getContentPanel() {
		return contentPanel;
	}

	Timer getFadeInTimer() {
		return timerFadeIn;
	}

	Timer getFadeOutTimer() {
		return timerFadeOut;
	}

	Timer getOpaqueTimer() {
		return timerOpaque;
	}

	public void showPane(GuiPane pane) {
		clear();
		startPaneTimer(pane);
	}

	void clear() {
		if (timerFadeIn != null) {
			timerFadeIn.stop();
		}
		if (timerFadeOut != null) {
			timerFadeOut.stop();
		}
		if (timerOpaque != null) {
			timerOpaque.stop();
		}
		if (contentFrame != null) {
			contentFrame.dispose();
		}
	}

	private void startPaneTimer(GuiPane pane) {
		try {
			contentFrame = new JFrame(pane.title);
			contentFrame.setResizable(false);
			contentFrame.setUndecorated(true);
			contentFrame.setSize(pane.width, pane.height);
			contentFrame.setFocusableWindowState(false);
			contentFrame.setVisible(true);
			contentFrame.setLayout(null);
			contentFrame.addMouseListener(guiListener);
			contentPanel = new ContentPane(pane);
			contentPanel.setSize(pane.width, pane.height);

			Rectangle rect = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
			x = (int) rect.getMaxX() - contentFrame.getWidth() - screenOffsetX;
			y = (int) rect.getMaxY() - contentFrame.getHeight() - screenOffsetY;

			contentFrame.setLocation(x - fadeInTicks * fadeInMoveSpeedX, y - fadeInTicks * fadeInMoveSpeedY);
			contentPanel.setVisible(true);
			contentFrame.add(contentPanel);
			contentFrame.setOpacity(0);
			contentFrame.setShape(new RoundRectangle2D.Double(0.0D, 0.0D, pane.width, pane.height, arcSize, arcSize));
			contentFrame.setCursor(new Cursor(12));
			opacity = 0.0F;
			ticks = 0;
			
			timerFadeIn = new Timer(tickLength, guiListener);
			timerOpaque = new Timer(tickLength, guiListener);
			timerFadeOut = new Timer(tickLength, guiListener);
			timerFadeIn.start();
			
			contentFrame.setAlwaysOnTop(true);
		} catch (Exception e) {
			System.out.println(ChatColor.RED + "Unexpected exception occured while showing a pane titled '" + pane.title + "'!");
			System.out.println(ChatColor.RED + "Your GUI is not compatible with Java swing/awt. Sorry :(");
		}
	}

	void actionPerformed(ActionEvent event) {
		if (event.getSource() == timerFadeIn) {
			contentFrame.toFront();
			opacity += maxOpacity / fadeInTicks;
			if (opacity >= maxOpacity) {
				opacity = maxOpacity;
				timerFadeIn.stop();
				timerOpaque.start();
			}
			contentFrame.setOpacity(opacity);
			Point p = contentFrame.getLocation();
			int actualMoveSpeedX = fadeInMoveSpeedX;
			int actualMoveSpeedY = fadeInMoveSpeedY;
			
			if (fadeInMoveSpeedY < 0)
				if (p.getY() <= y)
					actualMoveSpeedY = 0;

			else if (fadeInMoveSpeedY > 0 && p.getY() >= y) 
				actualMoveSpeedY = 0;
			

			if (fadeInMoveSpeedX < 0)
				if (p.getX() <= x)
					actualMoveSpeedX = 0;

			else if (fadeInMoveSpeedX > 0 && p.getX() >= x)
				actualMoveSpeedX = 0;

			p.translate(actualMoveSpeedX, actualMoveSpeedY);
			contentFrame.setLocation(p);
		} else if (event.getSource() == timerOpaque) {
			contentFrame.toFront();
			ticks += 1;
			if (ticks >= opaqueTicks) {
				timerOpaque.stop();
				timerFadeOut.start();
			}
			contentFrame.setOpacity(opacity);
		} else if (event.getSource() == timerFadeOut) {
			contentFrame.toFront();
			opacity -= maxOpacity / fadeOutTicks;
			if (opacity <= 0.0F) {
				opacity = 0.0F;
				timerFadeOut.stop();
				contentFrame.dispose();
			} else {
				contentFrame.setOpacity(opacity);
				Point point = contentFrame.getLocation();
				point.translate(fadeOutMoveSpeedX, fadeOutMoveSpeedY);
				contentFrame.setLocation(point);
			}
		}
	}
}
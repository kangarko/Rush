package net.rush.gui;

import java.awt.Cursor;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.geom.RoundRectangle2D;

import javax.swing.Timer;

import net.rush.gui.ContentPanel.ContentFrame;
import net.rush.gui.ContentPanel.NotificationCorner;

import org.bukkit.ChatColor;

public class Notifications {

	private NotificationsListener notificationsListener;
	//private ArrayList<Notification> notificationQueue = new ArrayList<Notification>();
	private NotificationCorner notificationsLocation;
	private ContentFrame contentFrame;
	private ContentPanel contentPanel;
	private Timer timerFadeIn;
	private Timer timerOpaque;
	private Timer timerFadeOut;
	private float opacity;
	private int ticks;
	private int tickLength = 30;
	private int x;
	private int y;
	private int fadeInTicks;
	private int fadeOutTicks;
	private int opaqueTicks;
	private float maxOpacity = 1.0F;

	private int arcSize = 20;

	private int screenOffsetX = 13;

	private int screenOffsetY = 13;
	private int fadeInMoveSpeedX;
	private int fadeInMoveSpeedY;
	private int fadeOutMoveSpeedX;
	private int fadeOutMoveSpeedY;
	//private boolean notificationBeingShown = false;

	public Notifications() {
		loadConfiguration();
		notificationsListener = new NotificationsListener(this);
		Notification notification = new Notification("GUI successfully started!");
		showOwnNotification(notification);
	}
	
	public NotificationCorner getNotificationCorner() {
		return this.notificationsLocation;
	}

	ContentFrame getContentFrame() {
		return this.contentFrame;
	}

	ContentPanel getContentPanel() {
		return this.contentPanel;
	}

	Timer getFadeInTimer() {
		return this.timerFadeIn;
	}

	Timer getFadeOutTimer() {
		return this.timerFadeOut;
	}

	Timer getOpaqueTimer() {
		return this.timerOpaque;
	}

	/*public boolean isNotificationBeingShown() {
		return this.notificationBeingShown;
	}*/

	public boolean showNotification(Notification notification) {
		//notificationQueue.clear();
		//notificationQueue.add(notification);
		//showNextNotificationInQueue();
		clear();
		startNotification(notification);
		return true;
	}

	void showOwnNotification(Notification notification) {
		//notificationQueue.clear();
		//this.notificationQueue.add(notification);
		//showNextNotificationInQueue();
		clear();
		startNotification(notification);
	}

	/*private void showNextNotificationInQueue() {
		if ((!this.notificationBeingShown) && (this.notificationQueue.size() > 0)) {
			startNotification((Notification) this.notificationQueue.get(0));
			this.notificationQueue.remove(0);
		} else {
			clear();
		}
	}*/
	
	void clear() {
		//notificationBeingShown = false;
		if(timerFadeIn != null)
			timerFadeIn.stop();
		if(timerFadeOut != null)
			timerFadeOut.stop();
		if(timerOpaque != null)
			timerOpaque.stop();
		if(contentFrame != null)
			contentFrame.dispose();
	}

	private void startNotification(Notification notification) {
		try {
			this.contentFrame = new ContentFrame(notification.getTitle());
			this.contentFrame.setResizable(false);
			this.contentFrame.setUndecorated(true);
			this.contentFrame.setSize(notification.getWidth(), notification.getHeight());
			this.contentFrame.setFocusableWindowState(false);
			this.contentFrame.setVisible(true);
			this.contentFrame.setLayout(null);
			this.contentFrame.addMouseListener(this.notificationsListener);
			this.contentPanel = new ContentPanel(notification);
			this.contentPanel.setSize(notification.getWidth(), notification.getHeight());

			Rectangle rect = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
			switch (this.notificationsLocation.getData()) {
			case 1:
				this.x = ((int) rect.getMinX() + this.screenOffsetX);
				this.y = ((int) rect.getMinY() + this.screenOffsetY);
				break;
			case 2:
				this.x = ((int) rect.getMaxX() - this.contentFrame.getWidth() - this.screenOffsetX);
				this.y = ((int) rect.getMinY() + this.screenOffsetY);
				break;
			case 3:
				this.x = ((int) rect.getMinX() + this.screenOffsetX);
				this.y = ((int) rect.getMaxY() - this.contentFrame.getHeight() - this.screenOffsetY);
				break;
			case 4:
			default:
				this.x = ((int) rect.getMaxX() - this.contentFrame.getWidth() - this.screenOffsetX);
				this.y = ((int) rect.getMaxY() - this.contentFrame.getHeight() - this.screenOffsetY);
			}

			this.contentFrame.setLocation(this.x - this.fadeInTicks
					* this.fadeInMoveSpeedX, this.y - this.fadeInTicks
					* this.fadeInMoveSpeedY);
			this.contentPanel.setVisible(true);
			this.contentFrame.add(this.contentPanel);
			this.contentFrame.setOpacity(0);
			this.contentFrame.setShape( new RoundRectangle2D.Double(0.0D, 0.0D, notification .getWidth(), notification.getHeight(), this.arcSize, this.arcSize));
			this.contentFrame.setCursor(new Cursor(12));
			this.opacity = 0.0F;
			this.ticks = 0;
			this.timerFadeIn = new Timer(this.tickLength,
					this.notificationsListener);
			this.timerOpaque = new Timer(this.tickLength,
					this.notificationsListener);
			this.timerFadeOut = new Timer(this.tickLength,
					this.notificationsListener);
			this.timerFadeIn.start();

			//this.notificationBeingShown = true;
			this.contentFrame.setAlwaysOnTop(true);
		} catch (Exception e) {
			log(ChatColor.RED+ "Unexpected exception occured while showing a notification titled '" + notification.getTitle() + "'!");

			log(ChatColor.RED + "Your GUI is not compatible with Java swing/awt. Sorry :(");
		}
	}

	void log(String message) {
		System.out.println(message);
	}

	private void loadConfiguration() {
		this.fadeInMoveSpeedX = 0;
		this.fadeInMoveSpeedY = -1;
		this.fadeOutMoveSpeedX = 0;
		this.fadeOutMoveSpeedY = -1;
		this.opaqueTicks = 50;
		this.fadeInTicks = 10;
		this.fadeOutTicks = 10;
		this.notificationsLocation = NotificationCorner.TOP_LEFT;
	}

	void actionPerformed(ActionEvent event) {
		if (event.getSource() == this.timerFadeIn) {
			this.contentFrame.toFront();
			this.opacity += this.maxOpacity / this.fadeInTicks;
			if (this.opacity >= this.maxOpacity) {
				this.opacity = this.maxOpacity;
				this.timerFadeIn.stop();
				this.timerOpaque.start();
			}
			this.contentFrame.setOpacity(this.opacity);
			Point p = this.contentFrame.getLocation();
			int actualMoveSpeedX = this.fadeInMoveSpeedX;
			int actualMoveSpeedY = this.fadeInMoveSpeedY;
			if (this.fadeInMoveSpeedY < 0) {
				if (p.getY() <= this.y)
					actualMoveSpeedY = 0;
			} else if ((this.fadeInMoveSpeedY > 0) && (p.getY() >= this.y)) {
				actualMoveSpeedY = 0;
			}

			if (this.fadeInMoveSpeedX < 0) {
				if (p.getX() <= this.x)
					actualMoveSpeedX = 0;
			} else if ((this.fadeInMoveSpeedX > 0) && (p.getX() >= this.x)) {
				actualMoveSpeedX = 0;
			}

			p.translate(actualMoveSpeedX, actualMoveSpeedY);
			this.contentFrame.setLocation(p);
		} else if (event.getSource() == this.timerOpaque) {
			this.contentFrame.toFront();
			this.ticks += 1;
			if (this.ticks >= this.opaqueTicks) {
				this.timerOpaque.stop();
				this.timerFadeOut.start();
			}
			this.contentFrame.setOpacity(this.opacity);
		} else if (event.getSource() == this.timerFadeOut) {
			this.contentFrame.toFront();
			this.opacity -= this.maxOpacity / this.fadeOutTicks;
			if (this.opacity <= 0.0F) {
				this.opacity = 0.0F;
				this.timerFadeOut.stop();
				//this.notificationBeingShown = false;
				this.contentFrame.dispose();
				//showNextNotificationInQueue();
			} else {
				this.contentFrame.setOpacity(this.opacity);
				Point p = this.contentFrame.getLocation();
				p.translate(this.fadeOutMoveSpeedX, this.fadeOutMoveSpeedY);
				this.contentFrame.setLocation(p);
			}
		}
	}
}
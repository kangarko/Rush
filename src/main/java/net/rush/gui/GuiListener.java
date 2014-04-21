package net.rush.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

class NotificationsListener implements ActionListener, MouseListener {
	private Notifications plugin;

	NotificationsListener(Notifications plugin) {
		this.plugin = plugin;
	}

	public void mouseClicked(MouseEvent event) {
		if (this.plugin.getFadeInTimer().isRunning()) {
			this.plugin.getFadeInTimer().stop();
		}
		if (this.plugin.getOpaqueTimer().isRunning()) {
			this.plugin.getOpaqueTimer().stop();
		}
		if (!this.plugin.getFadeOutTimer().isRunning())
			this.plugin.getFadeOutTimer().start();
	}

	public void mouseEntered(MouseEvent event) {
		this.plugin.getContentPanel().setMouseOverBackgroundColor();
	}

	public void mouseExited(MouseEvent event) {
		this.plugin.getContentPanel().setBackgroundColor();
	}

	public void mousePressed(MouseEvent event) {
	}

	public void mouseReleased(MouseEvent event) {
	}

	public void actionPerformed(ActionEvent event) {
		this.plugin.actionPerformed(event);
	}
}
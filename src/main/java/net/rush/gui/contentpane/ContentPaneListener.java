package net.rush.gui.contentpane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

class ContentPaneListener implements ActionListener, MouseListener {

	private RushGui plugin;

	ContentPaneListener(RushGui plugin) {
		this.plugin = plugin;
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		if (plugin.getFadeInTimer().isRunning()) {
			plugin.getFadeInTimer().stop();
		}

		if (plugin.getOpaqueTimer().isRunning()) {
			plugin.getOpaqueTimer().stop();
		}

		if (!plugin.getFadeOutTimer().isRunning()) {
			plugin.getFadeOutTimer().start();
		}
	}

	@Override
	public void mouseEntered(MouseEvent event) {
		plugin.getContentPanel().setMouseOverBackgroundColor();
	}

	@Override
	public void mouseExited(MouseEvent event) {
		plugin.getContentPanel().setBackgroundColor();
	}

	@Override
	public void mousePressed(MouseEvent event) {
	}

	@Override
	public void mouseReleased(MouseEvent event) {
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		plugin.actionPerformed(event);
	}
}
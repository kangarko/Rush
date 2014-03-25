package net.rush.model;

import net.rush.Server;


public interface CommandSender {

	public void sendMessage(String message);
	public String getName();
	public Server getServer();
}

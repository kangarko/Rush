package net.rush.api.model;

import net.rush.api.Server;

public interface CommandSender {

	public void sendMessage(String message);
	
	public Server getServer();
	
	public String getName();
	
	public boolean isPlayer();
}
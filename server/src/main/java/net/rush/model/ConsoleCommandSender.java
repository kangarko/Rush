package net.rush.model;

import net.rush.Server;

public class ConsoleCommandSender implements CommandSender {

	private final Server server;
	
	public ConsoleCommandSender(Server server) {
		this.server = server;
	}
	
	@Override
	public void sendMessage(String message) {
		getServer().getLogger().info(message);
	}

	@Override
	public Server getServer() {
		return server;
	}

	@Override
	public String getName() {
		return "CONSOLE";
	}

	@Override
	public boolean isPlayer() {
		return false;
	}

}

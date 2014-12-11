package net.rush.api.model;

import net.rush.api.Rush;
import net.rush.api.Server;

public class ConsoleCommandSender implements CommandSender {

	@Override
	public void sendMessage(String message) {
		getServer().getLogger().info(message);
	}

	@Override
	public Server getServer() {
		return Rush.getServer();
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

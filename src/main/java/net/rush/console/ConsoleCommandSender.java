package net.rush.console;

import java.util.logging.Logger;

import net.rush.Server;
import net.rush.model.CommandSender;

public class ConsoleCommandSender implements CommandSender {

	private final Server server;
	private Logger logger = Logger.getLogger("Minecraft");
	
	public ConsoleCommandSender(Server server) {
		this.server = server;
	}
	
	public void sendMessage(String message) {
		logger.info(message);
	}

	public void sendMessage(String[] msgs) {
		for(String msg : msgs) {
			logger.info(msg);
		}
	}
	
	public String getName() {
		return "CONSOLE";
	}

	public Server getServer() {
		return server;
	}

}

package net.rush.console;

import java.util.logging.Logger;

import net.rush.model.CommandSender;

public class ConsoleCommandSender implements CommandSender {

	public void sendMessage(String message) {
		Logger.getLogger("").info(message);
	}

	public void sendMessage(String[] msgs) {
		for(String msg : msgs) {
			Logger.getLogger("").info(msg);
		}
	}
	
	public String getName() {
		return "CONSOLE";
	}

}

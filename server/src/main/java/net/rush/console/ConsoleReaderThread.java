package net.rush.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import net.rush.RushServer;

public class ConsoleReaderThread extends Thread {

	private final RushServer server;

	public ConsoleReaderThread(RushServer server) {
		this.server = server;
		
		setName("Console Thread");
	}

	@Override
	public void run() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		while (server.isRunning) {
			try {
				String line = reader.readLine();
				server.commandManager.dispatchCommand(server.getConsoleCommandSender(), line);
				
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}

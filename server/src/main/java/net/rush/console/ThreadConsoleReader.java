package net.rush.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import net.rush.Server;

public class ThreadConsoleReader extends Thread {

	private final Server server;

	public ThreadConsoleReader(Server server) {
		this.server = server;
		
		setName("Console Thread");
	}

	@Override
	public void run() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		while (server.isRunning()) {
			try {
				String line = reader.readLine();
				server.getCommandManager().dispatchCommand(server.getConsoleSender(), line);
				
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}

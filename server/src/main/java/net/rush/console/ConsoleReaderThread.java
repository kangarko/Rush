package net.rush.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;

import net.rush.RushServer;

public class ConsoleReaderThread extends Thread {

	private final RushServer server;

	public ConsoleReaderThread(RushServer server) {
		this.server = server;
	}

	@Override
	public void run() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		while (server.isRunning) {
			try {
				String line = reader.readLine();

				if ("stop".equalsIgnoreCase(line)) // TODO Proper command handling.
					server.stop();
				else
					Logger.getGlobal().info("Unknown command. Type stop to shut down the server.");
				
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}

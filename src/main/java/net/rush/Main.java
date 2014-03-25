package net.rush;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.rush.console.ThreadConsoleReader;

public class Main {

	/**
	 * Creates a new server on TCP port 25565 and starts listening for
	 * connections.
	 * @param args The command-line arguments.
	 */
	public static void main(String[] args) {
		try {
			Server server = new Server();

			Thread threadConsoleReader = new ThreadConsoleReader(server);
			threadConsoleReader.start();

		} catch (Throwable t) {
			Logger.getGlobal().log(Level.SEVERE, "Error during server initializing", t);
		}
	}
	
}

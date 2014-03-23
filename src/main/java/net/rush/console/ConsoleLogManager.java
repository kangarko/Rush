package net.rush.console;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsoleLogManager {

	private final Logger logger;
	public static Logger global = Logger.getLogger("");

	public ConsoleLogManager(String s) {
		this.logger = Logger.getLogger(s);
		try {
			this.register();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private void register() throws IOException {
		this.logger.setUseParentHandlers(false);
		Handler[] handlers = this.logger.getHandlers();

		for (int index = 0; index < handlers.length; ++index) {
			Handler handler = handlers[index];

			this.logger.removeHandler(handler);
		}

		ConsoleLogFormatter consolelogformatter = new ConsoleLogFormatter();
		ConsoleHandler consolehandler = new ConsoleHandler();

		consolehandler.setFormatter(consolelogformatter);
		this.logger.addHandler(consolehandler);

		for (java.util.logging.Handler handler : global.getHandlers()) {
			global.removeHandler(handler);
		}

		consolehandler.setFormatter(consolelogformatter);
		global.addHandler(consolehandler);
	}

	public void info(String s) {
		this.logger.log(Level.INFO, s);
	}

	public void warning(String s) {
		this.logger.log(Level.WARNING, s);
	}

	public void warning(String s, Object... aobject) {
		this.logger.log(Level.WARNING, s, aobject);
	}

	public void warning(String s, Throwable throwable) {
		this.logger.log(Level.WARNING, s, throwable);
	}

	public void severe(String s) {
		this.logger.log(Level.SEVERE, s);
	}

	public void severe(String s, Throwable throwable) {
		this.logger.log(Level.SEVERE, s, throwable);
	}

	public void log(Level lvl, String s, Throwable throwable) {
		this.logger.log(lvl, s, throwable);
	}

	public void log(Level lvl, String s, Object[] obj) {
		this.logger.log(lvl, s, obj);
	}

	public void log(Level lvl, String s, Object obj) {
		this.logger.log(lvl, s, obj);
	}
}

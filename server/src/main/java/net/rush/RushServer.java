package net.rush;

import io.netty.util.ResourceLeakDetector;

import java.io.PrintStream;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.rush.api.Rush;
import net.rush.api.Server;
import net.rush.api.scheduler.Scheduler;
import net.rush.console.ConsoleReaderThread;
import net.rush.console.LogFormatter;
import net.rush.console.OutputStreamFormatter;
import net.rush.model.SessionRegistry;
import net.rush.netty.NettyInitializer;
import net.rush.scheduler.RushScheduler;


public class RushServer implements Server {

	public boolean isRunning = true;
	public int port = 25565;
	public SessionRegistry sessionRegistry = new SessionRegistry();
	
	private RushScheduler scheduler;
	private Logger logger = Logger.getLogger("Minecraft");
	
	public void init() {
		Rush.setServer(this);
		initLogging();
		
		logger.info("Initializing Rush for Minecraft 1.7.10");
		
		scheduler = new RushScheduler(this);
		scheduler.init();
		
		ConsoleReaderThread reader = new ConsoleReaderThread(this);
		reader.start();
		
		initConnection();
		
		logger.info("Ready for connections.");
	}

	public void initLogging() {
		Logger global = Logger.getLogger("");
		
		logger.setUseParentHandlers(false);

		for (Handler handler : logger.getHandlers())
			logger.removeHandler(handler);

		for (Handler handler : global.getHandlers())
			global.removeHandler(handler);

		LogFormatter formatter = new LogFormatter();

		ConsoleHandler handler = new ConsoleHandler();
		handler.setFormatter(formatter);

		logger.addHandler(handler);
		global.addHandler(handler);
		
		System.setOut(new PrintStream(new OutputStreamFormatter(logger, Level.INFO), true));
		System.setErr(new PrintStream(new OutputStreamFormatter(logger, Level.SEVERE), true));
	}
	
	public void initConnection() {
		ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.DISABLED); // Slows down performance.
		new NettyInitializer(this).start();
	}
	
	public void stop() {
		logger.info("Stopping Rush. Thank you and Good Bye!");
		isRunning = false;
		
		System.exit(0);
	}
	
	@Override
	public Scheduler getScheduler() {
		return scheduler;
	}
	
	@Override
	public Logger getLogger() {
		return logger;
	}
}

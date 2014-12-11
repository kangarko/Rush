package net.rush;

import io.netty.util.ResourceLeakDetector;

import java.io.PrintStream;
import java.util.HashSet;
import java.util.Random;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.rush.api.Rush;
import net.rush.api.Server;
import net.rush.api.model.ConsoleCommandSender;
import net.rush.api.scheduler.Scheduler;
import net.rush.cmd.CommandManager;
import net.rush.console.ConsoleReaderThread;
import net.rush.console.LogFormatter;
import net.rush.console.OutputStreamFormatter;
import net.rush.model.ChunkManager;
import net.rush.model.RushWorld;
import net.rush.model.SessionRegistry;
import net.rush.model.entity.RushPlayer;
import net.rush.model.gen.FlatWorldGenerator;
import net.rush.netty.NettyInitializer;
import net.rush.scheduler.RushScheduler;

import org.apache.commons.lang3.Validate;


public class RushServer implements Server {

	public boolean isRunning = true;
	public SessionRegistry sessionRegistry = new SessionRegistry();
	public RushWorld world;
	public Random rand = new Random();
	public CommandManager commandManager = new CommandManager();
	
	// ================================================ //
	public int port = 25565;
	public int viewDistance = 8;
	
	private RushScheduler scheduler;
	private ConsoleCommandSender consoleSender = new ConsoleCommandSender();
	private Logger logger = Logger.getLogger("Minecraft");
	
	void init() {
		Thread.currentThread().setName("Server Thread");
		
		Rush.setServer(this);		
		initLogging();
		
		logger.info("Initializing Rush for Minecraft 1.7.10");
		
		ConsoleReaderThread reader = new ConsoleReaderThread(this);
		reader.start();
		
		world = new RushWorld(this, new ChunkManager(new FlatWorldGenerator()));

		scheduler = new RushScheduler(this);
		scheduler.init();
		
		initConnection();
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				if (isRunning) {
					logger.info("Error: Unexpected server shutdown!");
					logger.info("Server shutdown initiated but");
					logger.info("the isRunning flag is still true!");
				}
			}
		});
		
		logger.info("Ready for connections.");
	}

	private void initLogging() {
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
	
	private void initConnection() {
		ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.DISABLED); // Slows down performance.
		new NettyInitializer(this).start();
	}
	
	public void stop() {
		isRunning = false;
		
		for (RushPlayer player : getPlayers())
			player.session.disconnect("Server closed");
		
		logger.info("Rush stopped. Thank you and good bye!");
		
		System.exit(0);
	}
	
	public RushPlayer getPlayer(String name) {
		Validate.notNull(name, "Name cannot be null");

		RushPlayer found = null;
		String lowerName = name.toLowerCase();
		int delta = Integer.MAX_VALUE;

		for (RushPlayer player : getPlayers())
			if (player.getName().toLowerCase().startsWith(lowerName)) {
				int curDelta = player.getName().length() - lowerName.length();
				if (curDelta < delta) {
					found = player;
					delta = curDelta;
				}
				if (curDelta == 0)
					break;
			}
		return found;
	}
	
	public HashSet<RushPlayer> getPlayers() {
		return world.getPlayersInWorld(); // TODO Multiworld.
	}
	
	public void broadcastMessage(String message) {
		for (RushPlayer player : getPlayers())
			player.sendMessage(message);
	}
	
	@Override
	public Scheduler getScheduler() {
		return scheduler;
	}
	
	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	public ConsoleCommandSender getConsoleCommandSender() {
		return consoleSender;
	}
}

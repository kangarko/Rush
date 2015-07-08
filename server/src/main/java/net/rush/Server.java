package net.rush;

import java.io.PrintStream;
import java.util.Random;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.Validate;

import io.netty.util.ResourceLeakDetector;
import lombok.Getter;
import net.rush.api.safety.SafeUnorderedZoznam;
import net.rush.cmd.CommandManager;
import net.rush.console.FormatterLog;
import net.rush.console.FormatterOutputStream;
import net.rush.console.ThreadConsoleReader;
import net.rush.entity.EntityPlayer;
import net.rush.model.ConsoleCommandSender;
import net.rush.model.SessionRegistry;
import net.rush.netty.NettyInitializer;
import net.rush.scheduler.Scheduler;
import net.rush.world.World;

public final class Server {

	/**
	 * Ordering rules:
	 * 
	 * 1. public (static first) final
	 * 2. public (static first)
	 * 
	 * (protected and 
	 * 				package)
	 * 
	 * 3. private final objects
	 * 4. private objects
	 * 
	 * 5. private final primitives
	 * 6. private final primitives
	 * 7. private
	 */

	public final Random rand = new Random();

	@Getter
	private final Logger logger = Logger.getLogger("Minecraft");	
	@Getter
	private final ConsoleCommandSender consoleSender = new ConsoleCommandSender(this);
	@Getter
	private final SessionRegistry sessionRegistry = new SessionRegistry();
	@Getter
	private final CommandManager commandManager = new CommandManager();		
	@Getter
	private final Scheduler scheduler = new Scheduler();
	@Getter
	private final World world;
	private final NettyInitializer nettyInitializer;
	private final Thread mainThread;

	@Getter
	private final int port = 25565;
	@Getter
	private final int viewDistance = 8;
	@Getter
	private boolean isRunning = true;

	public static void main(String[] args) {
		try {
			new Server();
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
	}

	private Server() throws Exception {
		mainThread = Thread.currentThread();
		mainThread.setName("Server");

		setupLogging();

		logger.info("Initializing Rush for Minecraft 1.7.10");

		ThreadConsoleReader reader = new ThreadConsoleReader(this);
		reader.start();

		commandManager.loadCommands();

		world = new World(this);

		ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.PARANOID); // Slows down performance.
		nettyInitializer = new NettyInitializer(this);
		nettyInitializer.start();

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

		try {
			while (isRunning) {
				Thread.sleep(50);

				long now = System.currentTimeMillis();

				try {
					pulse();
				} catch (Throwable t) {
					getLogger().log(Level.SEVERE, "Uncaught exception in scheduler", t);
					
					if (t instanceof StackOverflowError)
						System.exit(0);
					
				} finally {
					long lag = System.currentTimeMillis() - now;

					if (lag > 99)
						System.out.println("[Lag] Server froze for " + lag + " miliseconds.");
				}
			}
		} finally {
			stop();
		}
	}

	private void setupLogging() {
		Logger global = Logger.getLogger("");

		logger.setUseParentHandlers(false);

		for (Handler handler : logger.getHandlers())
			logger.removeHandler(handler);

		for (Handler handler : global.getHandlers())
			global.removeHandler(handler);

		FormatterLog formatter = new FormatterLog();

		ConsoleHandler handler = new ConsoleHandler();
		handler.setFormatter(formatter);

		logger.addHandler(handler);
		global.addHandler(handler);

		System.setOut(new PrintStream(new FormatterOutputStream(logger, Level.INFO), true));
		System.setErr(new PrintStream(new FormatterOutputStream(logger, Level.SEVERE), true));
	}

	private void pulse() {
		// Execute commands.
		commandManager.pulse();
		
		// Pull all pending tasks.
		scheduler.pulse();

		// Pulse each connection and handle it.
		sessionRegistry.pulse();

		// Handle general game logic.
		world.pulse();
	}

	public void stop() {
		isRunning = false;

		for (EntityPlayer player : getPlayers())
			player.kickPlayer("Server closed");

		nettyInitializer.shutdown();
		
		logger.info("Rush stopped. Thank you and good bye!");

		System.exit(0);
	}

	public EntityPlayer getPlayer(String name) {
		Validate.notNull(name, "Name cannot be null");

		EntityPlayer found = null;
		String lowerName = name.toLowerCase();
		int delta = Integer.MAX_VALUE;

		for (EntityPlayer player : getPlayers())
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

	public SafeUnorderedZoznam<EntityPlayer> getPlayers() {
		return world.getPlayersInWorld(); // TODO Multiworld.
	}

	public void broadcastMessage(String message) {
		for (EntityPlayer player : getPlayers())
			player.sendMessage(message);
	}

	public boolean isMainThread() {
		return Thread.currentThread() == mainThread;
	}
}

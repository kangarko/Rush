package net.rush;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.rush.cmd.CommandManager;
import net.rush.console.ConsoleCommandSender;
import net.rush.console.ConsoleLogManager;
import net.rush.console.ThreadConsoleReader;
import net.rush.gui.RushGui;
import net.rush.io.McRegionChunkIoService;
import net.rush.model.Player;
import net.rush.net.MinecraftPipelineFactory;
import net.rush.net.Session;
import net.rush.net.SessionRegistry;
import net.rush.packets.packet.ChatPacket;
import net.rush.task.TaskScheduler;
import net.rush.util.NumberUtils;
import net.rush.world.ForestWorldGenerator;
import net.rush.world.World;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

/**
 * The core class of the Rush server.
 */
public final class Server {

	public final String serverId;
	private static Server server;
	
	private final Logger logger = Logger.getLogger("Minecraft");
	private final ConsoleCommandSender consoleSender = new ConsoleCommandSender(this);
	private final RushGui gui;
	private final ServerProperties properties;
	private final TaskScheduler scheduler = new TaskScheduler(this);
	private final CommandManager commandManager = new CommandManager(this);
	private final World world;

	/** The {@link ServerBootstrap} used to initialize Netty. */
	private final ServerBootstrap bootstrap = new ServerBootstrap();
	
	/** A group containing all of the channels. */
	private final ChannelGroup group = new DefaultChannelGroup();
	
	/** The network executor service - Netty dispatches events to this thread pool. */
	private final ExecutorService executor = Executors.newCachedThreadPool();
	
	/** A list of all the active {@link Session}s. */
	private final SessionRegistry sessions = new SessionRegistry();
	
	private boolean saveEnabled = true;	// TODO: Does this belong in a different class e.g. the chunk IO service or the chunk manager?

	/**
	 * Creates a new server on TCP port 25565 and starts listening for
	 * connections.
	 * @param args The command-line arguments.
	 */
	public static void main(String[] args) {
		try {
			ConsoleLogManager.register();
			Server server = new Server();
			
			boolean jline = true;
			
			for(String arg : args) {
				if("nojline".equalsIgnoreCase(arg))
					jline = false;
			}
			
			Thread threadConsoleReader = new ThreadConsoleReader(server, jline);
			threadConsoleReader.start();

		} catch (Throwable t) {
			Logger.getGlobal().log(Level.SEVERE, "Error during server initializing", t);
		}
	}
	
	/**
	 * Creates and initializes a new server.
	 */
	public Server() {
		logger.info("Initializing Rush for Minecraft 1.6.4");
		long initialTime = System.currentTimeMillis();

		server = this;
		
		if (Runtime.getRuntime().maxMemory() / 1024L / 1024L < 512L)
			logger.warning("To start the server with more ram, launch it as \"java -Xmx1024M -Xms1024M -jar project-rush.jar\"");
		
		logger.info("Loading properties");		
		properties = new ServerProperties("server.properties");
		properties.load();
		
		world = new World(new McRegionChunkIoService(new File("world")), new ForestWorldGenerator());
        
        logger.info("Generating server id");
        serverId = Long.toString(new Random().nextLong(), 16);
        
		/* initialize channel and pipeline factories */
		ChannelFactory factory = new NioServerSocketChannelFactory(executor, executor);
		bootstrap.setFactory(factory);

		ChannelPipelineFactory pipelineFactory = new MinecraftPipelineFactory(this);
		bootstrap.setPipelineFactory(pipelineFactory);

		logger.info("Starting Minecraft server on " + (properties.serverIp.length() == 0 ? "*" : properties.serverIp) + ":" + properties.port);		
		try {
			group.add(bootstrap.bind(properties.serverIp.length() == 0 ? new InetSocketAddress(properties.port) : new InetSocketAddress(properties.serverIp, properties.port)));
		} catch (Throwable ex) {
			logger.warning("**** FAILED TO BIND TO PORT!");
			logger.warning("The exception was: " + ex.getCause().toString());
			logger.warning("Perhaps a server is already running on that port?");
			System.exit(0);
		}
		
		/* add shutdown hook */
		Runtime.getRuntime().addShutdownHook(new Thread(new ServerShutdownHandler()));
		
		/* start scheduling */
		scheduler.start();
		
		gui = new RushGui();
		
		logger.info("Ready for connections. (Took " + NumberUtils.msToSeconds(System.currentTimeMillis() - initialTime) + "s !)");
	}

	/**
	 * Gets the channel group.
	 * @return The {@link ChannelGroup}.
	 */
	public ChannelGroup getChannelGroup() {
		return group;
	}

	/**
	 * Gets the session registry.
	 * @return The {@link SessionRegistry}.
	 */
	public SessionRegistry getSessionRegistry() {
		return sessions;
	}

	/**
	 * Gets the task scheduler.
	 * @return The {@link TaskScheduler}.
	 */
	public TaskScheduler getScheduler() {
		return scheduler;
	}

	/**
	 * Gets the command manager.
	 * @return The {@link CommandManager}.
	 */
	public CommandManager getCommandManager() {
		return commandManager;
	}

	/**
	 * Gets the world this server manages.
	 * @return The {@link World} this server manages.
	 */
	public World getWorld() {
		return world;
	}

	/**
	 * Checks if saving is currently enabled.
	 * @return {@code true} if so, {@code false} if not.
	 */
	public boolean isSaveEnabled() {
		return saveEnabled;
	}

	/**
	 * Broadcasts a message to every player.
	 * 
	 * @param text The message text.
	 */
	public void broadcastMessage(String text) {
		ChatPacket message = new ChatPacket(text);
		for (Player player : getWorld().getPlayers())
			player.getSession().send(message);
	}
	
	/**
	 * Sets the saving enabled flag.
	 * @param saveEnabled The saving enabled flag.
	 */
	public void setSaveEnabled(boolean saveEnabled) {
		this.saveEnabled = saveEnabled;
	}

	public RushGui getGui() {
		return gui;
	}
	
	public ConsoleCommandSender getConsoleSender() {
		return consoleSender;
	}
	
	public Logger getLogger() {
		return logger;
	}

	public ServerProperties getProperties() {
		return properties;
	}
	
	public static Server getServer() {
		return server;
	}
	
	/**
	 * A {@link Runnable} which saves chunks on shutdown.
	 */
	private class ServerShutdownHandler implements Runnable {
		@Override
		public void run() {
			logger.info("Server is shutting down.");
			// Save chunks on shutdown.
			if (saveEnabled) {
				logger.info("Saving chunks...");
				try {
					world.getChunks().saveAll();
				} catch (IOException e) {
					logger.log(Level.WARNING, "Failed to save some chunks.", e);
				}
				logger.info("Finished!");
			}
		}
	}

}


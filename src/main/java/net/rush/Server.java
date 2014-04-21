package net.rush;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
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
	public boolean onlineMode = false;
	
	private final Logger logger = Logger.getLogger("Minecraft");
	private final ConsoleCommandSender consoleSender = new ConsoleCommandSender(this);
	private final SocketAddress socketAddress = new InetSocketAddress(25565);
	private final RushGui gui;
	
	/**
	 * The {@link ServerBootstrap} used to initialize Netty.
	 */
	private final ServerBootstrap bootstrap = new ServerBootstrap();

	/**
	 * A group containing all of the channels.
	 */
	private final ChannelGroup group = new DefaultChannelGroup();

	/**
	 * The network executor service - Netty dispatches events to this thread
	 * pool.
	 */
	private final ExecutorService executor = Executors.newCachedThreadPool();

	/**
	 * A list of all the active {@link Session}s.
	 */
	private final SessionRegistry sessions = new SessionRegistry();

	/**
	 * The task scheduler used by this server.
	 */
	private final TaskScheduler scheduler = new TaskScheduler(this);

	/**
	 * The command manager.
	 */
	private final CommandManager commandManager = new CommandManager(this);

	/**
	 * The world this server is managing.
	 */
	private final World world;

	/**
	 * Whether the server should automatically save chunks, e.g. at shutdown.
	 */
	private boolean saveEnabled = true;	// TODO: Does this belong in a different class e.g. the chunk IO service or the chunk manager?

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
	
	/**
	 * Creates and initializes a new server.
	 */
	public Server() {
		ConsoleLogManager.register();
		logger.info("Initializing Rush for Minecraft 1.6.4");
		long initialTime = System.currentTimeMillis();
		
		world = new World(new McRegionChunkIoService(new File("world")), new ForestWorldGenerator());

        if (Runtime.getRuntime().maxMemory() / 1024L / 1024L < 512L) {
            logger.warning("To start the server with more ram, launch it as \"java -Xmx1024M -Xms1024M -jar project-rush.jar\"");
        }
        
        logger.info("Generating server id");
        serverId = Long.toString(new Random().nextLong(), 16);
        
		/* initialize channel and pipeline factories */
		ChannelFactory factory = new NioServerSocketChannelFactory(executor, executor);
		bootstrap.setFactory(factory);

		ChannelPipelineFactory pipelineFactory = new MinecraftPipelineFactory(this);
		bootstrap.setPipelineFactory(pipelineFactory);

        /* try to bind to a port (TODO: Configurable in the future) */
		logger.info("Binding to address: " + socketAddress);
		try {
			group.add(bootstrap.bind(socketAddress));
		} catch (Throwable ex) {
			logger.warning("**** FAILED TO BIND TO PORT!");
			logger.warning("The exception was: " + ex.toString());
			logger.warning("Perhaps a server is already running on that port?");
			throw new RuntimeException(ex);
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


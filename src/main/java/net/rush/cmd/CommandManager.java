package net.rush.cmd;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.rush.Server;
import net.rush.model.CommandSender;

/**
 * A class which manages in-game commands.

 */
public final class CommandManager {

	/**
	 * A map of commands to their handlers.
	 */
	private final Map<String, Command> commands = new HashMap<String, Command>();
	
	private final Server server;

	/**
	 * Creates the command manager and populates it with a basic set of core
	 * commands.
	 */
	public CommandManager(Server server) {
		this.server = server;
		bind(new MeCommand());
		bind(new SaveAllCommand());
		bind(new SaveOffCommand());
		bind(new SaveOnCommand());
		bind(new TimeCommand());
		bind(new KickCommand());
		bind(new HelpCommand(this));
		bind(new GamemodeCommand());
		bind(new SpawnCommand());
		bind(new MetaCommand());
		bind(new ListCommand());
		bind(new TestCommand());
		bind(new RemoveCommand());
		bind(new GiveCommand());
		bind(new ClearCommand());
	}

	/**
	 * Executes a command.
	 * @param player The player that is trying to execute the command.
	 * @param text The unparsed command string.
	 */
	public void execute(CommandSender player, String text) {
		String[] args = text.substring(1).split(" ");
		String command = args[0];

		Command handler = commands.get(command);
		if (handler != null) {
			String[] shiftedArgs = new String[args.length - 1];
			System.arraycopy(args, 1, shiftedArgs, 0, shiftedArgs.length);
			handler.execute(player, shiftedArgs);
		} else {
			player.sendMessage("&eI don't understand that command. Try /help for assistance.");
		}
	}

	/**
	 * Gets an unmodifiable collection of valid commands.
	 * @return A collection of valid commands.
	 */
	public Collection<Command> getCommands() {
		return Collections.unmodifiableCollection(commands.values());
	}

	/**
	 * Adds a new command to this manager.
	 * @param command The command to add.
	 */
	public void bind(Command command) {
		commands.put(command.getCommand(), command);
	}
	
	public Server getServer() {
		return server;
	}

}


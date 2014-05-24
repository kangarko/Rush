package net.rush.cmd;

import net.rush.model.CommandSender;

/**
 * Represents a single command.

 */
public abstract class Command {
	
	private final String command;
	private final String[] aliases;

	/**
	 * Creates a new command.
	 * @param command The name of the new command without the slash prefix.
	 */
	public Command(String command) {
		this.command = command;
		this.aliases = null;
	}
	
	/**
	 * Creates a new command.
	 * @param command The name of the new command without the slash prefix.
	 * @param aliases The available aliases.
	 */
	public Command(String command, String... aliases) {
		this.command = command;
		this.aliases = aliases;
	}

	/**
	 * Gets the name of this command.
	 * @return The name of this command.
	 */
	public final String getCommand() {
		return command;
	}
	
	public final String[] getAliases() {
		return aliases;
	}

	/**
	 * Executes this command.
	 * @param player The player that issued the command.
	 * @param args An array of this command's arguments.
	 */
	public abstract void execute(CommandSender sender, String[] args);

}


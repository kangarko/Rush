package net.rush.cmd;

import java.util.TreeSet;

import net.rush.model.CommandSender;

public class CommandHelp extends Command {

	private final CommandManager commandManager;
	
	public CommandHelp(CommandManager commandManager) {
		super("help");
		
		this.commandManager = commandManager;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		TreeSet<String> commands = new TreeSet<>(commandManager.getCommands().keySet());
		
		sender.sendMessage("Commands: " + commands);		
	}

}

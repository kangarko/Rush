package net.rush.cmd;

import java.util.TreeSet;

import net.rush.api.model.CommandSender;

public class HelpCommand extends Command {

	private final CommandManager commandManager;
	
	public HelpCommand(CommandManager commandManager) {
		super("help");
		
		this.commandManager = commandManager;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		TreeSet<String> commands = new TreeSet<>(commandManager.commands.keySet());
		
		sender.sendMessage("Commands: " + commands);
	}

}

package net.rush.cmd;

import java.util.Arrays;
import java.util.HashMap;

import net.rush.api.model.CommandSender;
import net.rush.api.model.ConsoleCommandSender;

public class CommandManager {

	public HashMap<String, Command> commands = new HashMap<>();
	
	public CommandManager() {
		register(new GamemodeCommand());
		//register(new KickCommand()); // FIXME Fix non working second kick.
		register(new StopCommand());
	}
	
	private void register(Command command) {
		commands.put(command.label, command);
	}
	
	public void dispatchCommand(CommandSender sender, String rawMessage) {
		String[] args = rawMessage.split(" ");
		String label = args[0];	
		Command command = commands.get(label);
		
		if (command == null) {
			sender.sendMessage("Unknown command. Check for typos and try again.");
			return;
		}
		
		if (sender instanceof ConsoleCommandSender && command.noConsole) {
			sender.sendMessage("This command is not accesible from console.");
			return;
		}
		
		String[] rangedArgs = Arrays.copyOfRange(args, 1, args.length);
		
		command.execute(sender, rangedArgs);
	}
}

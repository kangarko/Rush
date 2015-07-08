package net.rush.cmd;

import java.util.Arrays;

import lombok.Data;
import net.rush.api.safety.SafeMapa;
import net.rush.api.safety.SafeOrderedZoznam;
import net.rush.entity.EntityPlayer;
import net.rush.model.CommandSender;

public final class CommandManager {

	private final SafeMapa<String, Command> commands = new SafeMapa<>();
	private final SafeOrderedZoznam<PendingCommand> pendingCommands = new SafeOrderedZoznam<>();
	
	public void loadCommands() {
		register(new CommandGamemode());
		register(new CommandKick());
		register(new CommandStop());
		register(new CommandTime());
		register(new CommandHelp(this));
	}
	
	private void register(Command command) {
		commands.put(command.getLabel(), command);
	}
	
	public void pulse() {
		synchronized (pendingCommands) {
			for (PendingCommand pendingCmd : pendingCommands) {
				String[] args = pendingCmd.getRawCommand().split(" ");
				String label = args[0];	
				
				Command command = commands.get(label);
				CommandSender sender = pendingCmd.getSender();
				
				if (command == null) {
					sender.sendMessage("Unknown command. Type /help for help.");
					continue;
				}
				
				if (!(sender instanceof EntityPlayer) && command.isPlayerRequired()) {
					sender.sendMessage("This command can only be accessed by a player.");
					continue;
				}
				
				String[] rangedArgs = Arrays.copyOfRange(args, 1, args.length);
				
				command.execute(sender, rangedArgs);
			}
			pendingCommands.clear();
		}
	}
	
	public void dispatchCommand(CommandSender sender, String rawCommand) {
		synchronized (pendingCommands) {
			pendingCommands.add(new PendingCommand(sender, rawCommand));
		}
	}
	
	SafeMapa<String, Command> getCommands() {
		return commands;
	}
}

@Data
class PendingCommand {
	private final CommandSender sender;
	private final String rawCommand;
}

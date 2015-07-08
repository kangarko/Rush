package net.rush.cmd;

import net.rush.Server;
import net.rush.model.CommandSender;

public class CommandTime extends Command {

	public CommandTime() {
		super("time");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {		
		Server server = (Server) sender.getServer();
		
		if (args.length == 0) {
			sender.sendMessage("The time is " + server.getWorld().getTime());
			sender.sendMessage("Use: /time <time> to set the time.");
			return;
		}

		try {
			int time = Integer.parseInt(args[0]);
		
			server.getWorld().setTime(time);
			sender.sendMessage("Time set to " + time);
			
		} catch (NumberFormatException ex) {
			sender.sendMessage("Invalid time value: " + args[0]);
		}
	}

}

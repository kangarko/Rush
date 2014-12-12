package net.rush.cmd;

import net.rush.RushServer;
import net.rush.api.model.CommandSender;

public class TimeCommand extends Command {

	public TimeCommand() {
		super("time");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {		
		RushServer server = (RushServer) sender.getServer();
		
		if (args.length == 0) {
			sender.sendMessage("The time is " + server.world.getTime());
			sender.sendMessage("Use: /time <time> to set the time.");
			return;
		}

		try {
			int time = Integer.parseInt(args[0]);
		
			server.world.setTime(time);
			sender.sendMessage("Time set to " + time);
			
		} catch (NumberFormatException ex) {
			sender.sendMessage("Invalid time value: " + args[0]);
		}
	}

}

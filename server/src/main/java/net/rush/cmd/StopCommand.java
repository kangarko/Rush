package net.rush.cmd;

import net.rush.api.model.CommandSender;

public class StopCommand extends Command {

	public String confirmedBy = "";
	
	public StopCommand() {
		super("stop");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {		

		if (!confirmedBy.equals(sender.getName())) {
			sender.sendMessage("Beware: Server will shut down. Type command again to confirm.");
			confirmedBy = sender.getName();
		} else
			sender.getServer().stop();
	}
	
}

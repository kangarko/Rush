package net.rush.cmd;

import net.rush.model.CommandSender;

public class CommandStop extends Command {

	public String confirmedBy = "";
	
	public CommandStop() {
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

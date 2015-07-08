package net.rush.cmd;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

import net.rush.Server;
import net.rush.entity.EntityPlayer;
import net.rush.model.CommandSender;

public class CommandKick extends Command {

	public CommandKick() {
		super("kick");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {		
		if (args.length == 0) {
			sender.sendMessage("Usage: /kick <player> [reason]");
			return;
		}
		
		Server server = sender.getServer();
		EntityPlayer victim = server.getPlayer(args[0]);
		
		if (victim == null) {
			sender.sendMessage("Cannot find player \"" + args[0] + "\". No kick.");
			return;
		}
		
		String reason = null;
		
		if (args.length > 1)
			reason = StringUtils.join(Arrays.copyOfRange(args, 1, args.length), ' ');
		
		victim.kickPlayer("Kicked by " + sender.getName() + (reason == null ? "" : "\\n\\nReason: " + reason));
	}
	
}

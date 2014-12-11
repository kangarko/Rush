package net.rush.cmd;

import java.util.Arrays;

import net.rush.RushServer;
import net.rush.api.model.CommandSender;
import net.rush.model.entity.RushPlayer;
import net.rush.protocol.packets.Kick;

import org.apache.commons.lang3.StringUtils;

public class KickCommand extends Command {

	public KickCommand() {
		super("kick");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {		
		if (args.length == 0) {
			sender.sendMessage("Usage: /kick <player> [reason]");
			return;
		}
		
		RushServer server = (RushServer) sender.getServer();
		RushPlayer victim = server.getPlayer(args[0]);
		
		if (victim == null) {
			sender.sendMessage("Cannot find player \"" + args[0] + "\". No kick.");
			return;
		}
		
		String reason = null;
		
		if (args.length > 1)
			reason = StringUtils.join(Arrays.copyOfRange(args, 1, args.length), ' ');
		
		victim.session.sendPacket(new Kick("Kicked by " + sender.getName() + (reason == null ? "" : "\\n\\nReason: " + reason)) );
	}
	
}

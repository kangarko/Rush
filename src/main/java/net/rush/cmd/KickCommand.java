package net.rush.cmd;

import net.rush.model.CommandSender;
import net.rush.model.Player;

/**
 * A command that kicks a user off the server.

 */
public final class KickCommand extends Command {

	/**
	 * Creates the {@code /kick} command.
	 */
	public KickCommand() {
		super("kick");
	}

	@Override
	public void execute(CommandSender player, String[] args) {
		// TODO check if the player executing this command is an admin
		if (args.length == 0) {
			player.sendMessage("&eUsage: /kick <username>");
			return;
		}

		String name = args[0];
		Player victim = player.getServer().getWorld().getPlayer(name);

		if(victim == null) {
			player.sendMessage("&eCan't find user " + name + ". No kick.");
			return;
		}

		String reason = "";
		
		for(int i = 1; i < args.length; i++)
			reason+= " " + args[i];

		victim.getSession().disconnect("&4Kicked by " + player.getName() + (reason == "" ? "" : "\n\n&7Reason:&f" + reason));

		player.sendMessage("&eCan't find user " + name + ". No kick.");
	}

}


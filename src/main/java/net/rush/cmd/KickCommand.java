package net.rush.cmd;

import net.rush.Server;
import net.rush.model.CommandSender;
import net.rush.model.Player;
import net.rush.packets.packet.impl.KickPacketImpl;

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
		if (args.length != 1) {
			player.sendMessage("§eUsage: /kick <username>");
			return;
		}

		String name = args[0];

		for (Player p : Server.instance.getWorld().getRushPlayers()) {
			if (p.getName().equalsIgnoreCase(name)) {
				player.sendMessage("§eKicking " + p.getName());
				p.getSession().send(new KickPacketImpl("Kicked by " + player.getName()));
				return;
			} else if (p.getName().toLowerCase().contains(name.toLowerCase())) {
				player.sendMessage("§ePartial match, kicking " + p.getName());
				p.getSession().send(new KickPacketImpl("Kicked by " + player.getName()));
				return;
			}
		}

		player.sendMessage("§eCan't find user " + name + ". No kick.");
	}

}


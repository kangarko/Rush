package net.rush.cmd;

import net.rush.model.CommandSender;
import net.rush.model.Player;

/**
 * A command that displays help on using other commands.

 */
public final class ClearCommand extends Command {

	public ClearCommand() {
		super("ci");
	}

	@Override
	public void execute(CommandSender player, String[] args) {
		if(!(player instanceof Player)) {
			player.sendMessage("This command cannot be used from console.");
			return;
		}
		Player pl = (Player) player;
		
		if(args.length == 0) {
			pl.getInventory().clear();
			pl.sendMessage("&7Inventory cleaned.");
		} else {
			try {
				String name = args[0];
				
				Player victim = player.getServer().getWorld().getPlayer(name);
				victim.getInventory().clear();
				
				pl.sendMessage("&7Inventory of " + name + " cleaned.");
			} catch (Exception ex) {
				pl.sendMessage("&cCannot find player " + args[0] + ".");
			}
		}
	}

}


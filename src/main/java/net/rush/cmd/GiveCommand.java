package net.rush.cmd;

import org.bukkit.Material;

import net.rush.model.CommandSender;
import net.rush.model.ItemStack;
import net.rush.model.Player;

/**
 * A command that displays help on using other commands.

 */
public final class GiveCommand extends Command {

	public GiveCommand() {
		super("i");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender player, String[] args) {
		if(!(player instanceof Player)) {
			player.sendMessage("This command cannot be used from console.");
			return;
		}
		Player pl = (Player) player;
		if(args.length == 0) {
			pl.sendMessage("&cUsage: /i <item>");
		} else if(args.length == 1) {
			try {
				pl.getInventory().addItem(new ItemStack(Material.getMaterial(args[0].toUpperCase()).getId()));
				pl.sendMessage("&3Rush // &2Given 1x of " + args[0].toUpperCase() + " to " + player.getName());
			} catch (NullPointerException ex) {
				pl.sendMessage("&cInvalid item: " + args[0]);
			}
		}
	}

}


package net.rush.cmd;

import org.bukkit.Material;

import net.rush.model.Block;
import net.rush.model.CommandSender;
import net.rush.model.Item;
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
		} else {
			try {
				int amount = args.length == 2 ? Integer.parseInt(args[1]) : 1;
				int data = args.length == 3 ? Integer.parseInt(args[2]) : 0;
				ItemStack item = new ItemStack(Material.getMaterial(args[0].toUpperCase()).getId(), amount, data);
				
				if(Item.byId[item.getId()] == null && Block.byId[item.getId()] == null) {
					pl.sendMessage("&4" + (Item.byId[item.getId()] == null ? "Item " : "Block") + args[0].toUpperCase() + " is not yet implemented! ");
					return;
				}
				
				pl.getInventory().addItem(item);
				
				pl.sendMessage("&3Rush // &2Given " + (Item.byId[item.getId()] == null ? "block " : "item ") + item + " to " + player.getName());
			} catch (NullPointerException ex) {
				pl.sendMessage("&cInvalid item: " + args[0]);
			} catch (NumberFormatException ex) {
				pl.sendMessage("&cInvalid item amount: " + args[1] + " or damage: " + args[2]);
			}
		}
	}

}


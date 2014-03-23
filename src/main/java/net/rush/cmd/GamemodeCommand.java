package net.rush.cmd;

import net.rush.model.CommandSender;
import net.rush.model.Player;

import org.bukkit.GameMode;

/**
 * A command that displays help on using other commands.

 */
public final class GamemodeCommand extends Command {

	public GamemodeCommand() {
		super("gm");
	}

	@Override
	public void execute(CommandSender player, String[] args) {
		if(!(player instanceof Player)) {
			player.sendMessage("This command cannot be used from console.");
			return;
		}
		Player pl = (Player) player;
		if(args.length == 0) {
			pl.sendMessage("&cUsage: /gamemode <id>");
		} else if(args.length == 1) {
			if("0".equalsIgnoreCase(args[0])) {
				pl.setGamemode(GameMode.SURVIVAL);
			} else if ("1".equalsIgnoreCase(args[0])) {
				pl.setGamemode(GameMode.CREATIVE);
			} else {
				pl.sendMessage("&cInvalid gamemode: " + args[0]);
			}
		}
	}

}


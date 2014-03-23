package net.rush.cmd;

import net.rush.model.CommandSender;
import net.rush.model.Player;
import net.rush.model.entity.Pig;

/**
 * A command that allows users to modify metadata
 * of in game entities on the fly.
 */
public final class MetaCommand extends Command {

	/**
	 * Creates the {@code /me} command.
	 */
	public MetaCommand() {
		super("meta");
	}

	@Override
	public void execute(CommandSender player, String[] args) {
		if (!(player instanceof Player)) {
			player.sendMessage("Cannot assesible from console.");
			return;
		}
		if(args.length != 2) {
			player.sendMessage("&cUsage: /meta <entityId> <pigHasSaddle>");
			return;
		}
		Player pl = (Player) player;
		if(!(pl.getWorld().getRushEntities().getEntity(Integer.valueOf(args[0])) instanceof Pig)) {
			pl.sendMessage("&cEntity with that ID isnt a pig (or ID is invalid)!");
			return;
		}
		
		Pig pig = (Pig) pl.getWorld().getRushEntities().getEntity(Integer.valueOf(args[0]));
		pig.setSaddle(Boolean.valueOf(args[1]));
		
		pl.sendMessage("&3Rush // &9Updated metadata of " + pig.getType());
	}

}


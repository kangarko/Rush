package net.rush.cmd;

import org.bukkit.Material;

import net.rush.model.CommandSender;
import net.rush.model.Entity;
import net.rush.model.Item;
import net.rush.model.LivingEntity;
import net.rush.model.Player;
import net.rush.util.Parameter;

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
		if(args.length <= 3) {
			player.sendMessage("&cUsage: /meta <entityId> <index> <type> <value>");
			return;
		}
		Player pl = (Player) player;
		Entity entity = pl.getWorld().getRushEntities().getEntity(Integer.valueOf(args[0]));
		if(!(entity instanceof LivingEntity)) {
			pl.sendMessage("&cEntity must be living entity (or ID is invalid)!");
			return;
		}

		int index = Integer.valueOf(args[1]);
		String type = args[2];
		Object param = args[3];
		LivingEntity living = (LivingEntity) entity;
		try {
			if("int".equalsIgnoreCase(type)) {
				living.setMetadata(new Parameter<Integer>(Parameter.TYPE_INT, index, Integer.valueOf(String.valueOf(param))));
			} else if ("short".equalsIgnoreCase(type)) {
				living.setMetadata(new Parameter<Short>(Parameter.TYPE_SHORT, index, Short.valueOf(String.valueOf(param))));
			} else if ("float".equalsIgnoreCase(type)) {
				living.setMetadata(new Parameter<Float>(Parameter.TYPE_FLOAT, index, Float.valueOf(String.valueOf(param))));
			} else if ("item".equalsIgnoreCase(type)) {
				living.setMetadata(new Parameter<Item>(Parameter.TYPE_ITEM, index, new Item(Material.valueOf(args[4]).getId(), Integer.valueOf(args[5]), Integer.valueOf(args[6]))));
			} else if ("byte".equalsIgnoreCase(type)) {
				living.setMetadata(new Parameter<Byte>(Parameter.TYPE_BYTE, index, Byte.valueOf(String.valueOf(param))));
			} else if ("string".equalsIgnoreCase(type)) {
				living.setMetadata(new Parameter<String>(Parameter.TYPE_STRING, index, String.valueOf(param)));
			} else {
				pl.sendMessage("&cType must be either: integer or short or byte or string!");
			}
		} catch (Exception ex) {
			pl.sendMessage("&cMetadata wasnt updated. Check console for stack trace.");
			throw new Error("Error updating metadata of " + living.getType(), ex);
		}

		pl.sendMessage("&3Rush // &9Updated metadata of " + living.getType());
	}

}


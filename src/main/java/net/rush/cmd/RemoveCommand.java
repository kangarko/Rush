package net.rush.cmd;

import net.rush.model.CommandSender;
import net.rush.model.Entity;

import org.bukkit.entity.EntityType;

/**
 * A command that kicks a user off the server.

 */
public final class RemoveCommand extends Command {

	/**
	 * Creates the {@code /kick} command.
	 */
	public RemoveCommand() {
		super("remove");
	}

	@Override
	public void execute(CommandSender player, String[] args) {
		// TODO check if the player executing this command is an admin
		if (args.length != 1) {
			player.sendMessage("§eUsage: /remove entity");
			return;
		}

		EntityType type;
		
		try {
			type = EntityType.valueOf(args[0].toUpperCase());
		} catch (Exception ex) {
			player.sendMessage("&eCan't find entity named " + args[0] + ".");
			return;
		}

		int amount = 0;
		for(Entity en : player.getServer().getWorld().getEntities()) {
			if(en.getType() == type) {
				en.destroy();
				amount++;
			}
		}

		player.sendMessage("&3Successfully removed " + amount + " entity of type " + type);

	}

}


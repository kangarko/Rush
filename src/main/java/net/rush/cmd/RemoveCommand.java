package net.rush.cmd;

import net.rush.model.CommandSender;
import net.rush.model.Entity;

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
			player.sendMessage("§eUsage: /remove <entityId>");
			return;
		}

		try {
			Entity en = player.getServer().getWorld().getEntities().getEntity(Integer.valueOf(args[0]));
			en.destroy();
			player.sendMessage("&3Removing entity of type " + en.getType());
		} catch (Exception ex) {
			player.sendMessage("&eCan't find entity with ID " + args[0] + ".");
		}
	}

}


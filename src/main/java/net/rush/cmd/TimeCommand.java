package net.rush.cmd;

import net.rush.model.CommandSender;
import net.rush.world.World;

/**
 * A command that sets or increments the current time.


 */
public final class TimeCommand extends Command {

	/**
	 * Creates the {@code /time} command.
	 */
	public TimeCommand() {
		super("time");
	}

	@Override
	public void execute(CommandSender player, String[] args) {
		if (args.length != 2) {
			player.sendMessage("§eUsage: /time <add|set> <value>");
			return;
		}

		String mode = args[0];
		int value;
		try {
			value = Integer.parseInt(args[1]);
		} catch (NumberFormatException ex) {
			player.sendMessage("§eThat doesn't appear to be a valid number.");
			return;
		}

		World world = player.getServer().getWorld();
		if (mode.equals("add")) {
			world.setTime(world.getTime() + value);
		} else if (mode.equals("set")) {
			world.setTime(value);
		} else {
			player.sendMessage("&eInvalid mode - try 'add' or 'set'.");
		}
	}

}


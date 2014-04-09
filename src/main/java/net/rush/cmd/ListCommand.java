package net.rush.cmd;

import net.rush.model.CommandSender;
import net.rush.model.Player;

/**
 * A command that sets or increments the current time.


 */
public final class ListCommand extends Command {

	/**
	 * Creates the {@code /time} command.
	 */
	public ListCommand() {
		super("list");
	}

	@Override
	public void execute(CommandSender player, String[] args) {
		StringBuilder builder = new StringBuilder();
		builder.append("§ePlayers online: ");

		for (Player pl : player.getServer().getWorld().getPlayers()) {
			builder.append(pl.getName());
			builder.append(", ");
		}

		player.sendMessage(builder.toString());
	}

}


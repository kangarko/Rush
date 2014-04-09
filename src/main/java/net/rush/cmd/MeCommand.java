package net.rush.cmd;

import net.rush.model.CommandSender;
import net.rush.util.StringUtils;

/**
 * A command that allows users to send a message in a different format that
 * leads to a 'role-playing' style message.

 */
public final class MeCommand extends Command {

	/**
	 * Creates the {@code /me} command.
	 */
	public MeCommand() {
		super("me");
	}

	@Override
	public void execute(CommandSender player, String[] args) {
		String message = StringUtils.join(args, " ");
		player.getServer().broadcastMessage(" * " + player.getName() + " " + message);
	}

}


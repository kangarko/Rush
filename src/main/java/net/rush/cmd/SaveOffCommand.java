package net.rush.cmd;

import net.rush.model.CommandSender;

/**
 * A command that turns automatic saving of chunks off.

 */
public final class SaveOffCommand extends Command {

	/**
	 * Creates the {@code /save-off} command.
	 */
	public SaveOffCommand() {
		super("save-off");
	}

	@Override
	public void execute(CommandSender player, String[] args) {
		player.getServer().setSaveEnabled(false);
	}

}


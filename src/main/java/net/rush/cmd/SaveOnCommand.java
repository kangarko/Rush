package net.rush.cmd;

import net.rush.model.CommandSender;

/**
 * A command that turns on automatic chunk saving.

 */
public final class SaveOnCommand extends Command {

	/**
	 * Creates the {@code /save-on} command.
	 */
	public SaveOnCommand() {
		super("save-on");
	}

	@Override
	public void execute(CommandSender player, String[] args) {
		player.getServer().setSaveEnabled(true);
	}

}


package net.rush.cmd;

import net.rush.Server;
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
		Server.instance.setSaveEnabled(true);
	}

}


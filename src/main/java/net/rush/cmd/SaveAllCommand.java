package net.rush.cmd;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.rush.model.CommandSender;

/**
 * A command that saves all known chunks in the ChunkManager.

 */
public final class SaveAllCommand extends Command {

	/**
	 * The logger for this class.
	 */
	private static final Logger logger = Logger.getLogger(SaveAllCommand.class.getName());

	/**
	 * Creates the {@code /save-all} command.
	 */
	public SaveAllCommand() {
		super("save-all");
	}

	@Override
	public void execute(CommandSender player, String[] args) {
		// Should this start a separate thread instead?
		try {
			player.getServer().getWorld().getChunks().saveAll();
			player.sendMessage("&3Rush // &fChunks were successfully saved.");
		} catch (IOException e) {
			logger.log(Level.WARNING, "Failed to save some chunks.", e);
			player.sendMessage("§eFailed to save some chunks, see server log for more details.");
		}
	}

}


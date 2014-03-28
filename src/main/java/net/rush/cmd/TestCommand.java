package net.rush.cmd;

import net.rush.model.CommandSender;
import net.rush.model.Item;
import net.rush.model.Player;

/**
 * A command that displays help on using other commands.

 */
public final class TestCommand extends Command {

	public TestCommand() {
		super("test");
	}

	@Override
	public void execute(CommandSender player, String[] args) {
		if (!(player instanceof Player)) {
			player.sendMessage("Cannot assesible from console.");
			return;
		}
		Player pl = (Player) player;
		pl.setItemInHand(new Item(1));
	}

}


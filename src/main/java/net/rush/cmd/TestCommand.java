package net.rush.cmd;

import net.rush.model.CommandSender;
import net.rush.model.Player;

/**
 * Random debug command.
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
		
		//Player pl = (Player) player;
		//pl.getSession().send(new ItemCollectPacket(Integer.valueOf(args[0]), pl.getId()));
	}

}


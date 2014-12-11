package net.rush.cmd;

import net.rush.api.GameMode;
import net.rush.api.model.CommandSender;
import net.rush.model.entity.RushPlayer;

public class GamemodeCommand extends Command {

	public GamemodeCommand() {
		super("gm");
		
		noConsole = true;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {		
		if (args.length != 1) {
			sender.sendMessage("Usage: /gm <value>");
			return;
		}
		RushPlayer player = (RushPlayer) sender;
		String gm = args[0];
		
		if ("0".equals(gm) || "survival".equalsIgnoreCase(gm) || "s".equalsIgnoreCase(gm))
			player.setGamemode(GameMode.SURVIVAL);
		else if ("1".equals(gm) || "creative".equalsIgnoreCase(gm) || "c".equalsIgnoreCase(gm))
			player.setGamemode(GameMode.CREATIVE);
		else
			sender.sendMessage("Invalid gamemode.");
	}
	
}

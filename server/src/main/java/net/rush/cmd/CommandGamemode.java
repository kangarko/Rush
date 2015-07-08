package net.rush.cmd;

import net.rush.api.GameMode;
import net.rush.entity.EntityPlayer;
import net.rush.model.CommandSender;

public class CommandGamemode extends Command {

	public CommandGamemode() {
		super("gm");
		
		setPlayerRequired();
	}

	@Override
	public void execute(CommandSender sender, String[] args) {		
		if (args.length != 1) {
			sender.sendMessage("Usage: /gm <value>");
			return;
		}
		EntityPlayer player = (EntityPlayer) sender;
		String gm = args[0].toLowerCase();
		
		if ("0".equals(gm) || "survival".equals(gm))
			player.setGamemode(GameMode.SURVIVAL);
		else if ("1".equals(gm) || "creative".equals(gm))
			player.setGamemode(GameMode.CREATIVE);
		else
			sender.sendMessage("Invalid gamemode.");
	}
	
}

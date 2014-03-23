package net.rush.cmd;

import net.rush.model.CommandSender;
import net.rush.model.LivingEntity;
import net.rush.model.Player;

import org.bukkit.entity.EntityType;

/**
 * An experimental command that spawns a pig. This is a demonstration of
 * early functions of Rush. (if it don´t crash = success :D)
 */
public final class SpawnCommand extends Command {

	/**
	 * Creates the {@code /me} command.
	 */
	public SpawnCommand() {
		super("spawn");
	}

	@Override
	public void execute(CommandSender player, String[] args) {
		if (!(player instanceof Player)) {
			player.sendMessage("Cannot assesible from console.");
			return;
		}
		if(args.length != 1) {
			player.sendMessage("&cUsage: /spawn <entityType>");
			return;
		}
		if(args[0].equalsIgnoreCase("player"))
			throw new Error("Cannot spawn a player!");
		Player pl = (Player) player;
		try {
			LivingEntity entity = pl.getWorld().spawnEntity(pl.getPosition(), EntityType.valueOf(args[0].toUpperCase()));
			pl.sendMessage("&3Rush // &2Spawned test entity ping with entity id " + entity.getId());
		} catch (IllegalArgumentException ex) {
			pl.sendMessage("&cInvalid entity type. Use bukkit entity type names.");
		}
	}

}


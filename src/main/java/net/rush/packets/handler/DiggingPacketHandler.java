package net.rush.packets.handler;

import net.rush.model.Player;
import net.rush.net.Session;
import net.rush.packets.packet.PlayerDiggingPacket;
import net.rush.packets.packet.SoundOrParticleEffectPacket;
import net.rush.world.World;

import org.bukkit.GameMode;
import org.bukkit.Material;

/**
 * A {@link PacketHandler} which processes digging messages.
 */
public final class DiggingPacketHandler extends PacketHandler<PlayerDiggingPacket> {

	@SuppressWarnings("deprecation")
	@Override
	public void handle(Session session, Player player, PlayerDiggingPacket message) {
		if (player == null)
			return;

		World world = player.getWorld();

		int x = message.getX();
		int z = message.getZ();
		int y = message.getY();

		int idBroken = world.getTypeId(x, y, z);
		
		player.sendMessage("status: &3" + message.getStatus());
		
		if (player.getGamemode() == GameMode.CREATIVE || message.getStatus() == PlayerDiggingPacket.DONE_DIGGING) {			
			world.setAir(x, y, z);
			
			SoundOrParticleEffectPacket soundMsg = new SoundOrParticleEffectPacket(SoundOrParticleEffectPacket.DIG_SOUND, x, y, z, idBroken, false);
			
			for (Player p: world.getPlayers()) {
				if(p != player && player.isWithinDistance(p))
					p.getSession().send(soundMsg);
				
			}
			
			if(player.getGamemode() == GameMode.CREATIVE)
				player.sendMessage("block broken in creative: " + Material.getMaterial(idBroken) + " at X: " + x + " Y: " + y + " Z: " + z);
			else {
				player.getWorld().dropItem(x, y, z, idBroken);
				//player.sendMessage("survival block break: " + Block.byId[idBroken].getName() + " at X: " + x + " Y: " + y + " Z: " + z);
			}
		}
	}

}


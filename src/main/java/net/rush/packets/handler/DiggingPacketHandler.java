package net.rush.packets.handler;

import net.rush.chunk.Chunk;
import net.rush.model.Block;
import net.rush.model.ItemStack;
import net.rush.model.Player;
import net.rush.model.Position;
import net.rush.model.entity.ItemEntity;
import net.rush.net.Session;
import net.rush.packets.packet.BlockChangePacket;
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

		// TODO it might be nice to move these calculations somewhere else since they will need to be reused
		int chunkX = x / Chunk.WIDTH + ((x < 0 && x % Chunk.WIDTH != 0) ? -1 : 0);
		int chunkZ = z / Chunk.HEIGHT + ((z < 0 && z % Chunk.HEIGHT != 0) ? -1 : 0);

		int localX = (x - chunkX * Chunk.WIDTH) % Chunk.WIDTH;
		int localZ = (z - chunkZ * Chunk.HEIGHT) % Chunk.HEIGHT;

		Chunk chunk = world.getChunks().getChunk(chunkX, chunkZ);
		int oldType = chunk.getType(localX, localZ, y);
		
		if(player.getGamemode() == GameMode.CREATIVE || message.getStatus() == PlayerDiggingPacket.STATE_DONE_DIGGING)
			chunk.setType(localX, localZ, y, Block.AIR);
		
		if (player.getGamemode() == GameMode.CREATIVE) {
			SoundOrParticleEffectPacket soundMsg = new SoundOrParticleEffectPacket(SoundOrParticleEffectPacket.DIG_SOUND, x, (byte)y, z, oldType, false);
			BlockChangePacket blockChangePacket = new BlockChangePacket(x, y, z, world);
			for (Player p: world.getPlayers()) {
				p.getSession().send(blockChangePacket);
				if(p != player && player.isWithinDistance(p)) {
					p.getSession().send(soundMsg);
				}
			}
			
			player.sendMessage("block broken in creative: " + Material.getMaterial(oldType) + " at X: " + x + " Y: " + y + " Z: " + z);
			return;
		}
		
		if (message.getStatus() == PlayerDiggingPacket.STATE_DONE_DIGGING) {
			SoundOrParticleEffectPacket soundMsg = new SoundOrParticleEffectPacket(SoundOrParticleEffectPacket.DIG_SOUND, x, (byte)y, z, oldType, false);
			BlockChangePacket blockChangePacket = new BlockChangePacket(x, y, z, world);
			for (Player p: world.getPlayers()) {
				p.getSession().send(blockChangePacket);
				if(p != player && player.isWithinDistance(p)) {
					p.getSession().send(soundMsg);
				}
			}
			
			ItemEntity item = new ItemEntity(player.getWorld(), new ItemStack(Material.COBBLESTONE.getId(), 1));
			item.setPosition(new Position(x, y, z));
			//item.setRotation(new Rotation(player.getRotation().getYaw(), player.getRotation().getPitch(), player.getRotation().getIntRoll()));
			player.getSession().send(item.createSpawnMessage());
			item.handleMetadata();
			player.sendMessage("survival block break: " + Material.getMaterial(oldType) + " at X: " + x + " Y: " + y + " Z: " + z);
		}
	}

}


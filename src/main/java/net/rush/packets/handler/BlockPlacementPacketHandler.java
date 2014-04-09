package net.rush.packets.handler;

import net.rush.chunk.Chunk;
import net.rush.model.ItemStack;
import net.rush.model.Player;
import net.rush.model.Position;
import net.rush.net.Session;
import net.rush.packets.packet.BlockChangePacket;
import net.rush.packets.packet.PlayerBlockPlacementPacket;
import net.rush.packets.packet.impl.BlockChangePacketImpl;
import net.rush.world.World;

import org.bukkit.Material;

public final class BlockPlacementPacketHandler extends PacketHandler<PlayerBlockPlacementPacket> {

	@SuppressWarnings("deprecation")
	@Override
	public void handle(Session session, Player player, PlayerBlockPlacementPacket message) {
		World world = player.getWorld();

		int x = message.getX();
		int z = message.getZ();
		int y = message.getY();

		int chunkX = x / Chunk.WIDTH + ((x < 0 && x % Chunk.WIDTH != 0) ? -1 : 0);
		int chunkZ = z / Chunk.HEIGHT + ((z < 0 && z % Chunk.HEIGHT != 0) ? -1 : 0);

		int localX = (x - chunkX * Chunk.WIDTH) % Chunk.WIDTH;
		int localZ = (z - chunkZ * Chunk.HEIGHT) % Chunk.HEIGHT;

		if(message.getHeldItem() == ItemStack.NULL_ITEM || !Material.getMaterial(message.getHeldItem().getId()).isBlock())
			return;

		int blockId = message.getHeldItem().getId();
		
		Chunk chunk = world.getChunks().getChunk(chunkX, chunkZ);
		chunk.setType(localX, localZ, y + 1, blockId);
		
		Position pos = parseDirection(x, y, z, message.getDirection());
		
		BlockChangePacket bcmsg = new BlockChangePacketImpl((byte)pos.getX(), (byte)pos.getY(), (byte)pos.getZ(), (byte)blockId, (byte)message.getHeldItem().getDamage()); 
		for (Player p: world.getPlayers()) {
			p.getSession().send(bcmsg);
		}
		player.sendMessage("&bYou have placed " + Material.getMaterial(blockId));
	}

	private Position parseDirection(int x, int y, int z, int direction) {
		switch(direction) {
		case 0:
		case -1:
			y = y - 1;	// from bottom
			break;
		case 1: 
			y = y + 1;	// from top
			break;
		case 2: 
			z = z - 1;
			break;
		case 3: 
			z = z + 1;
			break;
		case 4: 
			x = x - 1;
			break;
		case 5: 
			x = x + 1;
			break;
		default:
			throw new Error("[Block place] Unknown direction: " + direction);
		}
		Position pos = new Position(x, y, z);
		return pos;
	}

}
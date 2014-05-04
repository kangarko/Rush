package net.rush.packets.handler;

import net.rush.model.Block;
import net.rush.model.ItemStack;
import net.rush.model.Player;
import net.rush.net.Session;
import net.rush.packets.packet.PlayerDiggingPacket;
import net.rush.world.World;

import org.bukkit.Effect;
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

		Block block = Block.byId[world.getTypeId(x, y, z)];
		
		if(block == null) {
			player.sendMessage("&cUnknown broken block: " + Material.getMaterial(world.getTypeId(x, y, z)));
			return;
		}

		player.getInventory().setItemInHand(new ItemStack(1));
		
		if(message.getStatus() == PlayerDiggingPacket.DROP_ITEM) {
			player.getInventory().remove(player.getItemInHand());
			player.throwItemFromPlayer(new ItemStack(Block.GRASS.id));
			return;
		}
		
		int metadata = world.getBlockData(x, y, z);
		
		if (player.getGamemode() == GameMode.CREATIVE || message.getStatus() == PlayerDiggingPacket.DONE_DIGGING) {
			
			world.setAir(x, y, z);
			world.playEffectExceptTo(Effect.STEP_SOUND, x, y, z, block.id, player);
			
			block.onBlockPreDestroy(world, x, y, z, metadata);
			block.onBlockDestroyedByPlayer(world, player, x, y, z, metadata);
			
			if(player.getGamemode() != GameMode.CREATIVE)
				block.dropBlock(world, x, y, z, metadata, 0);
			else
				player.sendMessage("Block broken in creative: " + block.getName() + " at X: " + x + " Y: " + y + " Z: " + z);
		}
	}

}


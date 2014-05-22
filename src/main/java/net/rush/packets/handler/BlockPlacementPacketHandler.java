package net.rush.packets.handler;

import net.rush.model.Block;
import net.rush.model.Item;
import net.rush.model.ItemStack;
import net.rush.model.Player;
import net.rush.net.Session;
import net.rush.packets.packet.BlockChangePacket;
import net.rush.packets.packet.PlayerBlockPlacementPacket;
import net.rush.util.StringUtils;
import net.rush.world.World;

import org.bukkit.GameMode;
import org.bukkit.Material;

public final class BlockPlacementPacketHandler extends PacketHandler<PlayerBlockPlacementPacket> {

	@SuppressWarnings("deprecation")
	@Override
	public void handle(Session session, Player player, PlayerBlockPlacementPacket packet) {
		World world = player.getWorld();

		int x = packet.getX();
		int z = packet.getZ();
		int y = packet.getY();

		if(packet.getDirection() == -1)
			return;

		int xOffset = (int) (packet.getCursorX() * 16.0F);
		int yOffset = (int) (packet.getCursorY() * 16.0F);
		int zOffset = (int) (packet.getCursorZ() * 16.0F);

		int direction = packet.getDirection();

		if (placeOrActivate(player, world, packet.getHeldItem(), x, y, z, direction, xOffset, yOffset, zOffset))
			if(player.getGamemode() != GameMode.CREATIVE)
				player.getInventory().takeItemInHand();
		
		if(packet.getHeldItem() == ItemStack.NULL_ITEMSTACK)
			return;

		int blockId = packet.getHeldItem().getId();
		
		player.getSession().send(new BlockChangePacket(x, y, z, world));

		if (direction == 0)
			--y;

		if (direction == 1)
			++y;

		if (direction == 2)
			--z;

		if (direction == 3)
			++z;

		if (direction == 4)
			--x;

		if (direction == 5)
			++x;

		player.getSession().send(new BlockChangePacket(x, y, z, world));

		if (Block.byId[blockId] != null && Block.byId[blockId].material.isSolid())
			player.sendMessage("&bPlaced " + Block.byId[blockId].getName() + " @ " + StringUtils.serializeLoc(x, y, z) + " &dside: " + packet.getDirection());
		else if (Item.byId[blockId] != null)
			player.sendMessage("&5Clicked with item " + Item.byId[blockId].getName());
		else if (Item.byId[blockId] == null) {
			player.sendMessage("&eItem &6" + Material.getMaterial(blockId) + " is not yet implemented!");
		} else
			player.sendMessage("&6Block " + Material.getMaterial(blockId) + " is not yet implemented!");
	}

	public boolean placeOrActivate(Player player, World world, ItemStack item, int x, int y, int z, int direction, float xOffset, float yOffset, float zOffset) {

		if (!player.isCrouching() || item == ItemStack.NULL_ITEMSTACK) {
			int blockId = world.getTypeId(x, y, z);

			if (blockId > 0 && Block.byId[blockId] != null && Block.byId[blockId].onBlockActivated(world, x, y, z, player, direction, xOffset, yOffset, zOffset))
				return true;
		}
		
		if (item == ItemStack.NULL_ITEMSTACK)
			return false;

		if(Item.byId[item.getId()] != null)
			Item.byId[item.getId()].onItemUse(item, player, world, x, y, z, direction, xOffset, yOffset, zOffset);

		return tryPlace(item, player, world, x, y, z, direction, xOffset, yOffset, zOffset);
	}
	
	public boolean tryPlace(ItemStack item, Player player, World world, int x, int y, int z, int direction, float xOffset, float yOffset, float zOffset) {
		int id = item.getId();

		if(world.getTypeId(x, y, z) == item.getId() && (id == Block.VINE.id || id == Block.TALL_GRASS.id || id == Block.DEAD_BUSH.id))
			return false;
		
		if (id == Block.SNOW.id && (world.getBlockData(x, y, z) & 7) < 1) {
			direction = 1;
		} else /*if (id != Block.VINE.id && id != Block.TALL_GRASS.id && id != Block.DEAD_BUSH.id)*/ {
			if (direction == 0)
				--y;

			if (direction == 1)
				++y;

			if (direction == 2)
				--z;

			if (direction == 3)
				++z;

			if (direction == 4)
				--x;

			if (direction == 5)
				++x;
		}
		
		if (item.getCount() == 0)
			return false;
		
		Block block = Block.byId[id];

		if(block == null)
			return false;
		
		if (y == 255 && block.material.isSolid())
			return false;
		
		//else if (world.canPlaceEntityOnSide(id, x, y, z, false, direction, player, item)) {

		int metadata = block.onBlockPlaced(world, x, y, z, direction, xOffset, yOffset, zOffset, item.getDamage());
		block.onPostBlockPlaced(world, x, y, z, metadata);
		
		world.setTypeAndData(x, y, z, id, metadata, false);

		world.playSound(x + 0.5D, y + 0.5D, z + 0.5D, block.sound.getPlaceSound(), (block.sound.getVolume() + 1.0F) / 2.0F, block.sound.getPitch() * 0.8F);

		return true;
		//}
	}

}
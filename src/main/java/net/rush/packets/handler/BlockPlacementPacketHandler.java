package net.rush.packets.handler;

import net.rush.model.Block;
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
		
		boolean valid = true; // TODO better way to make it
		
		if(packet.getHeldItem() == ItemStack.NULL_ITEM || packet.getDirection() == -1)
			return;
		
		if(Block.byId[packet.getHeldItem().getId()] == null || !Block.byId[packet.getHeldItem().getId()].material.isSolid())
			valid = false;
		
		int xOffset = (int) (packet.getCursorX() * 16.0F);
		int yOffset = (int) (packet.getCursorY() * 16.0F);
		int zOffset = (int) (packet.getCursorZ() * 16.0F);
		
		int blockId = packet.getHeldItem().getId();
		int direction = packet.getDirection();

		if(valid)
			placeOrActivate(player, world, packet.getHeldItem(), x, y, z, direction, xOffset, yOffset, zOffset);
		
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
		
		if(valid)
			player.sendMessage("&bPlaced " + Block.byId[blockId].getName() + " @ " + StringUtils.serializeLoc(x, y, z) + " &dside: " + packet.getDirection());
		else
			player.sendMessage("&6Block " + Material.getMaterial(blockId) + " is not yet implemented!");
	}
	
	public boolean placeOrActivate(Player player, World world, ItemStack item, int x, int y, int z, int direction, float cursorX, float cursorY, float cursorZ) {
		int blockId;

		if (!player.isCrouching() || player.getItemInHand() == null) {
			blockId = world.getTypeId(x, y, z);

			if (blockId > 0 && Block.byId[blockId].onBlockActivated(world, x, y, z, player, direction, cursorX, cursorY, cursorZ))
				return true;
		}

		if (item == null)
			return false;
		else if (player.getGamemode() == GameMode.CREATIVE) {
			blockId = item.getDamage();
			int stackSize = item.getCount();
			
			boolean placedSuccessfully = tryPlace(item, player, world, x, y, z, direction, cursorX, cursorY, cursorZ);
			
			item.damage = blockId;
			item.count = stackSize;
			
			return placedSuccessfully;
		} else
			return tryPlace(item, player, world, x, y, z, direction, cursorX, cursorY, cursorZ);
	}
	
	public boolean tryPlace(ItemStack item, Player player, World world, int x, int y, int z, int side, float xOffset, float yOffset, float zOffset) {
		int id = item.getId(); //world.getTypeId(x, y, z);

		if (id == Block.SNOW.id && (world.getBlockData(x, y, z) & 7) < 1) {
			side = 1;
		} else if (id != Block.VINE.id && id != Block.TALL_GRASS.id && id != Block.DEAD_BUSH.id) {
			if (side == 0) {
				--y;
			}

			if (side == 1) {
				++y;
			}

			if (side == 2) {
				--z;
			}

			if (side == 3) {
				++z;
			}

			if (side == 4) {
				--x;
			}

			if (side == 5) {
				++x;
			}
		}

		if (item.getCount() == 0)
			return false;
		else if (y == 255 && Block.byId[id].material.isSolid())
			return false;
		//else if (world.canPlaceEntityOnSide(id, x, y, z, false, side, player, item)) {
			Block block = Block.byId[id];
			int metadata = Block.byId[id].onBlockPlaced(world, x, y, z, side, xOffset, yOffset, zOffset, item.getDamage());

			world.setTypeAndData(x, y, z, id, metadata, true);
			
				if (world.getTypeId(x, y, z) == id) {
					Block.byId[id].onBlockPlacedBy(world, x, y, z, player, item);
					Block.byId[id].onPostBlockPlaced(world, x, y, z, metadata);
				}

				world.playSound(x + 0.5D, y + 0.5D, z + 0.5D, block.sound.getPlaceSound(), (block.sound.getVolume() + 1.0F) / 2.0F, block.sound.getPitch() * 0.8F);
				item.setCount(item.getCount() - 1);

			return true;
		//} else
			//return false;
	}

}
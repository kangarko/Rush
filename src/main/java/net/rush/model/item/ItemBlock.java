package net.rush.model.item;

import net.rush.model.Block;
import net.rush.model.Item;
import net.rush.model.ItemStack;
import net.rush.model.Player;
import net.rush.world.World;


public class ItemBlock extends Item {

	private int blockID;

	public ItemBlock(int itemId) {
		super(itemId);
		blockID = itemId + 256;
	}

	/**
	 * Returns the blockID for this Item
	 */
	public int getBlockID() {
		return blockID;
	}

	/**
	 * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
	 * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
	 */
	@Override
	public boolean onItemUse(ItemStack item, Player player, World world, int x, int y, int z, int direction, float xOffset, float yOffset, float zOffset) {
		int id = world.getTypeId(x, y, z);

		if (id == Block.SNOW.id && (world.getBlockData(x, y, z) & 7) < 1)
			direction = 1;
		
		else if (id != Block.VINE.id && id != Block.TALL_GRASS.id && id != Block.DEAD_BUSH.id) {
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

		if (item.count == 0)
			return false;
		//else if (!player.canPlayerEdit(x, y, z, side, item))
		//	return false;
		else if (y == 255 && Block.byId[blockID].material.isSolid())
			return false;
		//else if (world.canPlaceEntityOnSide(blockID, x, y, z, false, direction, player, item)) {
			Block block = Block.byId[blockID];
			int blockDamage = getMetadata(item.getDamage());
			int metadata = Block.byId[blockID].onBlockPlaced(world, x, y, z, direction, xOffset, yOffset, zOffset, blockDamage);

			if (world.setTypeAndData(x, y, z, blockID, metadata, false)) {
				if (world.getTypeId(x, y, z) == blockID) {
					Block.byId[blockID].onBlockPlacedBy(world, x, y, z, player, item);
					Block.byId[blockID].onPostBlockPlaced(world, x, y, z, metadata);
				}

				world.playSound(x + 0.5F, y + 0.5F, z + 0.5F, block.sound.getPlaceSound(), (block.sound.getVolume() + 1.0F) / 2.0F, block.sound.getPitch() * 0.8F);
				--item.count;
			}

			return true;
		/*} else
			return false;*/
	}

	@Override
	public String getUnlocalizedName(ItemStack item) {
		return Block.byId[blockID].getName();
	}

	@Override
	public String getUnlocalizedName() {
		return Block.byId[blockID].getName();
	}
}

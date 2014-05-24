package net.rush.model.item;

import net.rush.model.Block;
import net.rush.model.Item;


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

	/*@Override
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
		if (y == 255 && Block.byId[blockID].material.isSolid())
			return false;		
		//else if (world.canPlaceEntityOnSide(blockID, x, y, z, false, direction, player, item)) {
		Block block = Block.byId[blockID];

		int metadata = block.onBlockPlaced(world, x, y, z, direction, xOffset, yOffset, zOffset, getMetadata(item.getDamage()));

		world.setTypeAndData(x, y, z, blockID, metadata, false);
		if (world.getTypeId(x, y, z) == blockID) {
			block.onBlockPlacedBy(world, x, y, z, player, item);
			block.onPostBlockPlaced(world, x, y, z, metadata);
		}

		world.playSound(x + 0.5F, y + 0.5F, z + 0.5F, block.sound.getPlaceSound(), (block.sound.getVolume() + 1.0F) / 2.0F, block.sound.getPitch() * 0.8F);
		--item.count;


		return true;
		//} else return false
	}*/

	@Override
	public String getName() {
		return Block.byId[blockID].getName();
	}
}

package net.rush.model.item;

import net.rush.model.Block;
import net.rush.model.Item;
import net.rush.model.ItemStack;
import net.rush.model.Player;
import net.rush.world.World;

public class ItemSeeds extends Item {

	private int blockId;

	public ItemSeeds(int id, int blocktype) {
		super(id);
		blockId = blocktype;
	}

	@Override
	public boolean onItemUse(ItemStack item, Player player, World world, int x, int y, int z, int direction, float xOffset, float yOffset, float zOffset) {
		if (direction != 1)
			return false;
		
		if (world.getTypeId(x, y, z) == Block.SOIL.id && world.isAir(x, y + 1, z)) {
			world.setTypeId(x, y + 1, z, blockId, true);
			//--item.count;
			return true;
		}
		return false;

	}
}

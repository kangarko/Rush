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
			world.playSound(x + 0.5D, y + 0.5D, z + 0.5D, Block.Sound.GRASS.getPlaceSound(), (Block.Sound.GRASS.getVolume() + 1.0F) / 2.0F, Block.Sound.GRASS.getPitch() * 0.8F);
			return true;
		}
		return false;

	}
}

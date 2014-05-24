package net.rush.model.block;

import net.rush.model.Block;
import net.rush.model.ItemStack;
import net.rush.model.Material;
import net.rush.world.World;

public abstract class RotatableBlock extends Block {
	
	protected RotatableBlock(int id, Material material) {
		super(id, material);
	}

	@Override
	public int getRenderType() {
		return 31;
	}

	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int direction, float xOffset, float yOffset, float zOffset, int metadata) {
		int blockMetadata = metadata & 3;
		byte rotation = 0;

		switch (direction) {
			case 0:
			case 1:
				rotation = 0;
				break;

			case 2:
			case 3:
				rotation = 8;
				break;

			case 4:
			case 5:
				rotation = 4;
		}

		return blockMetadata | rotation;
	}

	@Override
	public int damageDropped(int damage) {
		return damage & 3;
	}

	
	public int getDamage(int type) {
		return type & 3;
	}

	/**
	 * Returns an item stack containing a single instance of the current block type. 'i' is the block's subtype/damage
	 * and is ignored for blocks which do not support subtypes. Blocks which cannot be harvested should return null.
	 */
	@Override
	protected ItemStack createStackedBlock(int type) {
		return new ItemStack(id, 1, getDamage(type));
	}
}

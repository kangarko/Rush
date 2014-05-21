package net.rush.model.block;

import net.rush.model.Material;
import net.rush.world.World;

public class BlockCrops extends RotatableBlock {

	public BlockCrops(int id) {
		super(id, Material.PLANT);
	}

	@Override
	public boolean grow(World world, int x, int y, int z, boolean bonemeal) {
		if (bonemeal)
			world.setTypeAndData(x, y, z, id, 7, true);
		else {
			int data = world.getBlockData(x, y, z);
			if(data < 7)
				world.setTypeAndData(x, y, z, id, data++, true);
		}
		return true;
	}
}

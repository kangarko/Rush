package net.rush.model.block;

import java.util.Random;

import net.rush.model.Block;
import net.rush.model.Material;
import net.rush.world.World;

public class BlockFlower extends Block {

	public BlockFlower(int id) {
		super(id, Material.PLANT);
	}

	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		int ground = world.getTypeId(x, y, z);
		return ground == Block.GRASS.id || ground == Block.DIRT.id || ground == Block.SOIL.id;
	}

	@Override
	public void tick(World world, int x, int y, int z, Random rand) {
		checkIfCanStay(world, x, y, z);
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int neighborBlockId) {
		checkIfCanStay(world, x, y, z);
	}
	
	private void checkIfCanStay(World world, int x, int y, int z) {
		if (!canPlaceBlockAt(world, x, y - 1, z)) {
			dropBlock(world, x, y, z, world.getBlockData(x, y, z), 0);
			world.setTypeWithNotify(x, y, z, 0, true);
		}
	}
}

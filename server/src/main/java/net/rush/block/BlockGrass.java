package net.rush.block;

import net.rush.world.World;

public class BlockGrass extends Block {

	protected BlockGrass(int id) {
		super(id);
	}

	@Override
	public boolean isTickingRandomly() {
		return true;
	}
	
	@Override
	public void onTick(World world, int x, int y, int z) {
		world.setType(x, y, z, DIRT.id);
	}
}

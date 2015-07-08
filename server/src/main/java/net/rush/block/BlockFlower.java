package net.rush.block;

import net.rush.world.World;

public class BlockFlower extends Block {

	protected BlockFlower(int id) {
		super(id);
	}
	
	@Override
	public boolean canPlaceAt(World world, int x, int y, int z) {
		int groundId = world.getType(x, y, z);
		
		return groundId == Block.GRASS.id || groundId == Block.DIRT.id;
	}

}

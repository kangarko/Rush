package net.rush.block;

import net.rush.world.World;

public class BlockCactus extends Block {

	protected BlockCactus(int id) {
		super(id);
	}
	
	@Override
	public boolean canPlaceAt(World world, int x, int y, int z) {
		int groundId = world.getType(x, y, z);
		
		return groundId == Block.SAND.id || groundId == Block.CACTUS.id;
	}

}

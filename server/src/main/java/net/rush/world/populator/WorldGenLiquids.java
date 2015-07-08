package net.rush.world.populator;

import java.util.Random;

import net.rush.block.Block;
import net.rush.world.WorldGenerator.WorldPopulate;

public class WorldGenLiquids extends WorldPopulator {

	private int liquidId;

	public WorldGenLiquids(int liquidId) {
		this.liquidId = liquidId;
	}

	@Override
	public boolean generate(WorldPopulate world, Random random, int x, int y, int z) {
		// rush start
		if (y < 1)
			return false;
		// rush end
		
		if (world.getType(x, y + 1, z) != Block.STONE.id)
			return false;
		else if (world.getType(x, y - 1, z) != Block.STONE.id)
			return false;
		else if (world.getType(x, y, z) != 0 && world.getType(x, y, z) != Block.STONE.id)
			return false;

		int stoneProb = 0;

		if (world.getType(x - 1, y, z) == Block.STONE.id)
			++stoneProb;

		if (world.getType(x + 1, y, z) == Block.STONE.id)
			++stoneProb;

		if (world.getType(x, y, z - 1) == Block.STONE.id)
			++stoneProb;

		if (world.getType(x, y, z + 1) == Block.STONE.id)
			++stoneProb;

		int nearProb = 0;

		if (world.getType(x - 1, y, z) == 0)
			++nearProb;

		if (world.getType(x + 1, y, z) == 0)
			++nearProb;

		if (world.getType(x, y, z - 1) == 0)
			++nearProb;

		if (world.getType(x, y, z + 1) == 0)
			++nearProb;

		if (stoneProb == 3 && nearProb == 1)
			world.setType(x, y, z, liquidId);

		return true;
	}
}

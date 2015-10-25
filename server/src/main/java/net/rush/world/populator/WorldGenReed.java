package net.rush.world.populator;

import java.util.Random;

import net.rush.world.World;
import net.rush.world.WorldGenerator.WorldPopulate;

public class WorldGenReed extends WorldPopulator {

	@Override
	public boolean generate(WorldPopulate world, Random random, int x, int y, int z) {
		/*for (int tries = 0; tries < 15; ++tries) {
			int xPos = x + random.nextInt(4) - random.nextInt(4);
			int yPos = y;
			int zPos = z + random.nextInt(4) - random.nextInt(4);
			int height = 2 + random.nextInt(random.nextInt(3) + 1);

			for (int piece = 0; piece < height; ++piece)
				if (Block.SUGAR_CANE_BLOCK.canPlaceBlockAt(world, xPos, yPos + piece - 1, zPos) && !isSurroundedWith(world, xPos, yPos + piece + 1, zPos, Material.WATER)
						&& !isSurroundedWith(world, xPos, yPos + piece, zPos, Material.WATER))
					world.setType(xPos, yPos + piece, zPos, Block.SUGAR_CANE_BLOCK.id, false);		
		}*/

		if (true)
			return true;
		
		return true;
	}
	
	private boolean isSurroundedWith(World w, int x, int y, int z/*, Material material*/) {
		//if (w.getMaterial(x - 1, y, z) == material || w.getMaterial(x + 1, y, z) == material || w.getMaterial(x, y, z - 1) == material || w.getMaterial(x, y, z + 1) == material)
		//	return true;
		return false;
	}	
}

package net.rush.world.populator;

import java.util.Random;

import net.rush.world.World;

public class WorldGenCactus extends WorldPopulator {

	@Override
	public boolean generate(World world, Random random, int x, int y, int z) {
		/*for (int tries = 0; tries < 15; ++tries) {
			int xPos = x + random.nextInt(8) - random.nextInt(8);
			int yPos = y + random.nextInt(4) - random.nextInt(4);
			int zPos = z + random.nextInt(8) - random.nextInt(8);

			// rush start
			if (yPos < 0)
				return true;
			// rush end
			
			if (world.getType(xPos, yPos, zPos) == 0) {
				int height = 2 + random.nextInt(2);
				
				for (int piece = 0; piece < height; ++piece)
					if (Block.CACTUS.canPlaceAt(world, xPos, yPos + piece - 1, zPos))
						world.setType(xPos, yPos + piece, zPos, Block.CACTUS.id);
			}
		}*/

		return true;
	}
}

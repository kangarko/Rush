package net.rush.world.populator;

import java.util.Random;

import net.rush.block.Block;
import net.rush.world.WorldGenerator.WorldPopulate;

public class WorldGenTrees extends WorldPopulator {

	@Override
	public boolean generate(WorldPopulate world, Random random, int x, int y, int z) {
		int height = random.nextInt(3) + 4;
		boolean canGrow = true;

		if (world.getType(x, y - 1, z) != Block.GRASS.id && world.getType(x, y - 1, z) != Block.DIRT.id)
			return false;

		if (y >= 1 && y + height + 1 <= 128) {
			int trunkY;
			int trunkX;
			int trunkZ;
			int idOrPos;

			for (trunkY = y; trunkY <= y + 1 + height; ++trunkY) {
				byte length = 1;

				if (trunkY == y)
					length = 0;

				if (trunkY >= y + 1 + height - 2)
					length = 2;

				for (trunkX = x - length; trunkX <= x + length && canGrow; ++trunkX)
					for (trunkZ = z - length; trunkZ <= z + length && canGrow; ++trunkZ)
						if (trunkY >= 0 && trunkY < 128) {
							idOrPos = world.getType(trunkX, trunkY, trunkZ);
							
							if (idOrPos != 0 && idOrPos != Block.LEAVES.id)
								canGrow = false;
						} else
							canGrow = false;
			}

			if (!canGrow)
				return false;

			trunkY = world.getType(x, y - 1, z);
			if ((trunkY == Block.GRASS.id || trunkY == Block.DIRT.id) && y < 128 - height - 1) {
				world.setType(x, y - 1, z, Block.DIRT.id);

				int yPos;

				for (yPos = y - 3 + height; yPos <= y + height; ++yPos) {
					trunkX = yPos - (y + height);
					trunkZ = 1 - trunkX / 2;

					for (idOrPos = x - trunkZ; idOrPos <= x + trunkZ; ++idOrPos) {
						int j2 = idOrPos - x;

						for (int zPos = z - trunkZ; zPos <= z + trunkZ; ++zPos) {
							int l2 = zPos - z;

							if ((Math.abs(j2) != trunkZ || Math.abs(l2) != trunkZ || random.nextInt(2) != 0 && trunkX != 0) && !Block.byId(world.getType(idOrPos, yPos, zPos)).isOpaqueCube())
								world.setType(idOrPos, yPos, zPos, Block.LEAVES.id);
						}
					}
				}

				for (yPos = 0; yPos < height; ++yPos) {
					trunkX = world.getType(x, y + yPos, z);
					if (trunkX == 0 || trunkX == Block.LEAVES.id)
						world.setType(x, y + yPos, z, Block.LOG.id);
				}

				return true;
			}
			return false;

		}
		return false;
	}
}

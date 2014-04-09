package net.rush.world;

import java.util.Random;

import net.rush.chunk.Chunk;
import net.rush.model.Block;

/**
 * A {@link WorldGenerator} that generates chunks with trees randomly placed.

 */
public final class ForestWorldGenerator extends FlatGrassWorldGenerator {

	private static final int MAX_TREES = 2;

	private static final int TREE_MIN_HEIGHT = 6;

	private static final int TREE_MAX_HEIGHT = 9;

	private static final int TREE_CANOPY_HEIGHT = 5;

	private static final int TREE_CANOPY_WIDTH = 5;

	private Random random = new Random();

	@Override
	public Chunk generate(int chunkX, int chunkZ) {
		Chunk chunk = super.generate(chunkX, chunkZ);

		int numTrees = random.nextInt(MAX_TREES + 1);
		for (int i = 0; i < numTrees; i++) {
			int x = random.nextInt(Chunk.WIDTH - (TREE_CANOPY_WIDTH * 2) + TREE_CANOPY_WIDTH);
			int z = random.nextInt(Chunk.HEIGHT- (TREE_CANOPY_WIDTH * 2) + TREE_CANOPY_WIDTH);
			int height = random.nextInt(TREE_MAX_HEIGHT - TREE_MIN_HEIGHT) + TREE_MIN_HEIGHT;
			int type = random.nextInt(3); // standard, redwood, birch
			makeTree(chunk, x, z, 61, height, type);
		}
		
		for (int cislo = 0; cislo < 8; cislo++) {
			int x = random.nextInt(Chunk.WIDTH);
			int z = random.nextInt(Chunk.HEIGHT);
			if (chunk.isBlockAir(x, z, 61) /*&& ((BlockFlower) Block.blocksList[plantBlockId]).canBlockStay(world, i1, j1, k1)*/)
				if(random.nextBoolean())
					chunk.setType(x, z, 61, 37 + random.nextInt(2));
		}
		
		for (int cislo = 0; cislo < 16; cislo++) {
			int x = random.nextInt(Chunk.WIDTH);
			int z = random.nextInt(Chunk.HEIGHT);
			if (chunk.isBlockAir(x, z, 61) /*&& ((BlockFlower) Block.blocksList[plantBlockId]).canBlockStay(world, i1, j1, k1)*/)
				chunk.setType(x, z, 61, 31);
				chunk.setMetaData(x, z, 61, 1);
		}

		return chunk;
	}

	/** Grows a tree in a chunk. */
	private static void makeTree(Chunk chunk, int x, int z, int y, int height, int type) {

		int center = (TREE_CANOPY_WIDTH) / 2;
		int trunkX = x + center;
		int trunkZ = z + center;

		for (int i = 0; i < height - TREE_CANOPY_HEIGHT; i++) {  // Generate the trunk
			chunk.setType(trunkX, trunkZ, y + i, Block.LOG);
			chunk.setMetaData(trunkX, trunkZ, y + i, type);
		}

		for (int cy = height - TREE_CANOPY_HEIGHT; cy < height; cy++) { // Generate leaves
			int startX = x;
			int endX = x + TREE_CANOPY_WIDTH;

			int startZ = z;
			int endZ = z + TREE_CANOPY_HEIGHT;

			// make the canopy smaller at the top or bottom
			if (cy == height - TREE_CANOPY_HEIGHT || cy == height - 1) {
				startX++;
				endX--;

				startZ++;
				endZ--;
			}

			for (int cx = startX; cx < endX; cx++) {
				for (int cz = startZ; cz < endZ; cz++) {
					if (cx == trunkX && cz == trunkZ && cy < (height - 2)) { // trunk, leave some leaves above it
						chunk.setTypeAndData(trunkX, trunkZ, y + cy, Block.LOG, type);
					} else {
						chunk.setTypeAndData(cx, cz, y + cy, Block.LEAVES, type);
					}
				}
			}
		}
	}

}


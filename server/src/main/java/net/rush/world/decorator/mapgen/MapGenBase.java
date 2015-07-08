package net.rush.world.decorator.mapgen;

import java.util.Random;

public class MapGenBase {

	protected int radius = 8;
	protected Random rand = new Random();

	public void initDecoration(long seed, int chunkX, int chunkZ, byte[] blockArray) {
		rand.setSeed(seed);
		long xNoise = rand.nextLong() / 2L * 2L + 1L;
		long zNoise = rand.nextLong() / 2L * 2L + 1L;

		for (int xPos = chunkX - radius; xPos <= chunkX + radius; ++xPos)
			for (int zPos = chunkZ - radius; zPos <= chunkZ + radius; ++zPos) {
				rand.setSeed(xPos * xNoise + zPos * zNoise ^ seed);
				decorate(seed, xPos, zPos, chunkX, chunkZ, blockArray);
			}
	}

	protected void decorate(long seed, int xPos, int zPos, int chunkX, int chunkZ, byte[] blockArray) {
	}
}

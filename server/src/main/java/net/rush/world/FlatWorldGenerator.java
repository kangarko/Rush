package net.rush.world;

import net.rush.world.chunk.Chunk;

public abstract class FlatWorldGenerator implements WorldGenerator {

	@Override
	public Chunk generate(int chunkX, int chunkZ) {
		throw new Error("Not implemented!");
		
		/*Chunk chunk = new Chunk(chunkX, chunkZ);

		for (int x = 0; x < Chunk.WIDTH; x++)
			for (int z = 0; z < Chunk.HEIGHT; z++)
				for (int y = 0; y < Chunk.DEPTH; y++) {
					int id = 0;
					int grassHeight = 54;
					int stoneHeight = 50;

					if (y == 0)
						id = 7;
					else if (y == grassHeight)
						id = 2;
					else if (y > stoneHeight && y < grassHeight)
						id = 3;
					else if (y <= stoneHeight)
						id = 1;

					chunk.setType(x, y, z, id);
					chunk.setSkylight(x, y, z, 15);
				}

		return chunk;*/
	}
}

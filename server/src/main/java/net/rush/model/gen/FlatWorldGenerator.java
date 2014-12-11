package net.rush.model.gen;

import net.rush.api.WorldGenerator;
import net.rush.api.world.Chunk;
import net.rush.model.RushChunk;

public class FlatWorldGenerator implements WorldGenerator {

	@Override
	public Chunk generate(int chunkX, int chunkZ) {
		RushChunk chunk = new RushChunk(chunkX, chunkZ);

		for (int x = 0; x < RushChunk.WIDTH; x++)
			for (int z = 0; z < RushChunk.HEIGHT; z++)
				for (int y = 0; y < RushChunk.DEPTH; y++) {
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
					chunk.setSkyLight(x, y, z, 15);
				}

		return chunk;
	}

}

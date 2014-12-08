package net.rush.model;

import java.util.HashMap;

import net.rush.api.ChunkCoords;

public final class ChunkManager {

	private RushWorld world;
	private HashMap<ChunkCoords, RushChunk> chunks = new HashMap<>();
	
	public ChunkManager(RushWorld world) {
		this.world = world;
	}

	public RushChunk getChunk(int x, int z) {
		ChunkCoords key = new ChunkCoords(x, z);
		RushChunk chunk = chunks.get(key);

		if (chunk == null) {
			chunk = generate(world, x, z);
			chunks.put(key, chunk);
		}

		return chunk;
	}
	
	private RushChunk generate(RushWorld world, int chunkX, int chunkZ) { // TODO
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


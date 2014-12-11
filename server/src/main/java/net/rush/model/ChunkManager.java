package net.rush.model;

import java.util.HashMap;

import net.rush.api.ChunkCoords;
import net.rush.api.WorldGenerator;

public final class ChunkManager {

	private WorldGenerator generator;
	private HashMap<ChunkCoords, RushChunk> chunks = new HashMap<>();
	
	public ChunkManager(WorldGenerator generator) {
		this.generator = generator;
	}

	public RushChunk getChunk(int x, int z) {
		ChunkCoords key = new ChunkCoords(x, z);
		RushChunk chunk = chunks.get(key);

		if (chunk == null) {
			chunk = (RushChunk) generator.generate(x, z);
			chunks.put(key, chunk);
		}

		return chunk;
	}
}


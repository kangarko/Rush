package net.rush.world.chunk;

import net.rush.api.safety.SafeMapa;
import net.rush.world.AlphaWorldGenerator;
import net.rush.world.World;
import net.rush.world.WorldGenerator;
import net.rush.world.WorldGenerator.WorldPopulate;

public final class ChunkManager {

	protected final World world;
	protected final WorldGenerator generator;
	protected final SafeMapa<ChunkCoords, Chunk> chunks = new SafeMapa<>();
	
	public ChunkManager(World world) {
		this.world = world;
		this.generator = new AlphaWorldGenerator(world);
	}

	public Chunk getChunk(int x, int z) {
		ChunkCoords key = new ChunkCoords(x, z);
		Chunk chunk = chunks.get(key);

		if (chunk == null) {			
			chunk = generator.generate(x, z);
			chunks.put(key, chunk);
		}

		return chunk;
	}
	
	public void populate(Chunk chunk) {	
		if (!chunk.isPopulated()) {			
			chunk.setPopulated();
			
			generator.populate(new WorldPopulate(world, chunk), chunk.getX(), chunk.getZ());
		}
	}
}


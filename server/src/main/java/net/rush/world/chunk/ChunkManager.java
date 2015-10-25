package net.rush.world.chunk;

import net.rush.api.safety.SafeMapa;
import net.rush.world.AlphaWorldGenerator;
import net.rush.world.World;
import net.rush.world.WorldGenerator;

public final class ChunkManager {

	protected final World world;
	protected final WorldGenerator generator;
	protected final SafeMapa<ChunkCoords, Chunk> chunks = new SafeMapa<>();
	
	private boolean populating = false;
	
	public ChunkManager(World world) {
		this.world = world;
		this.generator = new AlphaWorldGenerator(world);
	}

	public synchronized Chunk getChunk(int x, int z) {
		
		String threadName = Thread.currentThread().getName();
		if (!threadName.equals("Server"))
			System.out.println("threadname: " + threadName);
		
		ChunkCoords key = new ChunkCoords(x, z);
		Chunk chunk = chunks.get(key);

		if (chunk == null) {			
			chunk = generator.generate(x, z);

			if (!populating) {
				populating = true;
				
				populateIfNot(chunk);
			
				populating = false;
			}
			
			chunks.put(key, chunk);
		}

		return chunk;
	}
	
	public void populateIfNot(Chunk chunk) {	
		if (!chunk.isPopulated()) {			
			chunk.setPopulated();
			
			generator.populate(world, chunk.getX(), chunk.getZ());
		}
	}
}


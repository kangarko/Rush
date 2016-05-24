package net.rush.world.chunk;

import net.rush.api.safety.SafeMapa;
import net.rush.world.AlphaWorldGenerator;
import net.rush.world.World;
import net.rush.world.WorldGenerator;

public final class ChunkManager {

	private static final Object ZAMOK = new Object();

	private boolean populating = false;

	protected final World world;
	protected final WorldGenerator generator;
	protected final SafeMapa<ChunkCoords, Chunk> chunks = new SafeMapa<>();

	public ChunkManager(World world) {
		this.world = world;
		this.generator = new AlphaWorldGenerator(world);
	}

	public Chunk getChunk(int x, int z) {

		synchronized (ZAMOK) {
			ChunkCoords key = new ChunkCoords(x, z);
			Chunk chunk = chunks.get(key);

			if (chunk != null)
				return chunk;

			chunk = generator.generate(x, z);
			chunks.put(key, chunk);

			populate(chunk);

			return chunk;

			//Chunk prev = chunks.putIfAbsent(key, chunk);
			// if it was created in the intervening time, the earlier one wins
			//return prev == null ? chunk : prev;

		}
	}

	private void populate(Chunk ch) {
		synchronized (ZAMOK) {
			if (populating)
				return;

			populating = true;

			if (!ch.isPopulated()) {		
				ch.setPopulated();

				generator.populate(world, ch.getX(), ch.getZ());
			}

			populating = false;
		}
	}
}


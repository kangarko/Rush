package net.rush.model;

import java.util.HashSet;
import java.util.Iterator;

import net.rush.RushServer;
import net.rush.api.ChunkCoords;
import net.rush.api.Position;
import net.rush.model.entity.RushEntity;
import net.rush.model.entity.RushPlayer;

import org.apache.commons.lang3.Validate;

/**
 * A class which represents the in-game world.
 */
public class RushWorld {

	public final RushServer server;

	public final ChunkManager chunks;
	public final EntityManager entities = new EntityManager();
	
	public final HashSet<ChunkCoords> loadedChunks = new HashSet<>();
	
	public final Position spawnPosition = new Position(0, 60, 0);
			
	public RushWorld(RushServer server, ChunkManager chunks) {
		this.server = server;
		this.chunks = chunks;
	}

	public void pulse() {
		resetActiveChunks();
		
		/*for (RushEntity entity : entities)
			entity.pulse();
		
		for (RushEntity entity : entities)
			entity.reset();*/
		
		// TODO Which one?
		for (Iterator<RushEntity> it = entities.iterator(); it.hasNext(); ) {
			RushEntity entity = it.next();

			if (entity.isActive()) {
				entity.pulse();
			} else
				it.remove();
		}
		
		for (RushEntity entity : entities)
			entity.reset();
	}

	protected void resetActiveChunks() {
		loadedChunks.clear();

		int chunkX, chunkZ;

		// how many chunks from player should be ticked (redstone activated, grass grown etc)
		// is 7 in notchian server
		int activationRadius = 7;

		for (RushPlayer pl : getPlayersInWorld()) {
			chunkX = pl.position.intX() / RushChunk.WIDTH;
			chunkZ = pl.position.intZ() / RushChunk.HEIGHT;

			for (int x = (chunkX - activationRadius); x <= (chunkX + activationRadius); x++)
				for (int z = (chunkZ - activationRadius); z <= (chunkZ + activationRadius); z++) {
					ChunkCoords key = new ChunkCoords(x, z);
					
					loadedChunks.add(key);
				}
		}
	}

	public HashSet<RushPlayer> getPlayersInWorld() {
		return entities.getAll(RushPlayer.class);
	}

	public RushChunk getChunkFromBlockCoords(int x, int z) {
		return getChunkFromChunkCoords(x >> 4, z >> 4);
	}

	public RushChunk getChunkFromChunkCoords(int x, int z) {
		return chunks.getChunk(x, z);
	}

	public int getType(int x, int y, int z) {
		ensure(x, y, z);

		return getChunkFromBlockCoords(x, z).getType(x & 15, y, z & 15);
	}
	
	public void setType(int x, int y, int z, int type) {
		ensure(x, y, z);

		getChunkFromBlockCoords(x, z).setType(x & 15, y, z & 15, type);
	}

	public int getMetadata(int x, int y, int z) {
		ensure(x, y, z);

		return getChunkFromBlockCoords(x, z).getMetadata(x & 15, y, z & 15);
	}
	
	public void setMetadata(int x, int y, int z, int type, int data) {
		ensure(x, y, z);

		getChunkFromBlockCoords(x, z).setMetadata(x & 15, y, z & 15, data);
	}

	private void ensure(int x, int y, int z) {
		Validate.isTrue(y >= 0 && y < 256, "Invalid coords x:" + x + " y: " + y + " z: " + z);
	}

	/*protected void tickActiveChunks() {
		Iterator<ChunkCoords> it = activeChunks.iterator();

		while(it.hasNext()) {
			ChunkCoords coords = it.next();
			Chunk chunk = getChunkFromChunkCoords(coords);

			int posX = coords.getX() * Chunk.WIDTH;
			int posZ = coords.getZ() * Chunk.HEIGHT;

			// In 3 rounds, picks up random block in a chunk and tick it,
			// x y z is converted to world x y z
			// Since Minecraft 1.8 this is customizable in attribute "randomTick" (or similar)
			for(int count = 0; count < 3; count++) {

				int x = rand.nextInt(RushChunk.WIDTH);
				int y = rand.nextInt(RushChunk.DEPTH);
				int z = rand.nextInt(RushChunk.HEIGHT);

				int type = chunk.getType(x, y, z);

				Block block = Block.byId[type];				
				Validate.notNull(block, "Unknown block of type " + org.bukkit.Material.getMaterial(type));

				if(type > 0)
					block.randomPulse(this, posX + x, y, posZ + z, rand);
			}
		}
	}*/
}

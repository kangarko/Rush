package net.rush.world;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import org.apache.commons.lang3.Validate;

import lombok.Getter;
import net.rush.Server;
import net.rush.api.Position;
import net.rush.api.safety.SafeUnorderedZoznam;
import net.rush.block.Block;
import net.rush.entity.EntityManager;
import net.rush.entity.EntityPlayer;
import net.rush.protocol.packets.BlockChange;
import net.rush.protocol.packets.TimeUpdate;
import net.rush.world.chunk.Chunk;
import net.rush.world.chunk.ChunkCoords;
import net.rush.world.chunk.ChunkManager;

/**
 * A class which represents the in-game world.
 */
public final class World {

	@Getter
	private final Server server;
	@Getter
	private final Position spawnPosition = new Position(63, 106, -107);
	@Getter
	private final EntityManager entities = new EntityManager();
	private final ChunkManager chunkManager;
	private final SafeUnorderedZoznam<ChunkCoords> loadedChunks = new SafeUnorderedZoznam<>();
	private final Random rand = new Random();

	@Getter
	private long seed = 0;
	@Getter
	private int time;

	public World(Server server) {
		this.server = server;
		this.chunkManager = new ChunkManager(this);

		generateSpawnArea();
	}

	private void generateSpawnArea() {
		int radius = server.getViewDistance();
		int lastProgress = 0;

		for (int x = -radius; x <= radius; ++x) {
			int progress = (x + radius) * 100 / (radius + radius + 1);
			int difference = progress - lastProgress;

			if (difference > 15) {
				server.getLogger().info("Preparing spawn area: " + progress + "%");
				lastProgress = progress;
			}

			for (int z = -radius; z <= radius; ++z)
				getChunkFromChunkCoords(((int) spawnPosition.x >> 4) + x, ((int) spawnPosition.z >> 4) + z);
		}
	}

	public void pulse() {
		resetActiveChunks();
		tickActiveChunks();
		advanceTime();

		entities.pulse();
		
		while (!packetPendingTask.isEmpty())
			packetPendingTask.poll().run();
	}

	private void resetActiveChunks() {
		loadedChunks.clear();

		int chunkX, chunkZ;

		// how many chunks from player should be ticked (redstone activated, grass grown etc)
		// is 7 in notchian server
		int activationRadius = 7;

		for (EntityPlayer pl : getPlayersInWorld()) {
			chunkX = pl.getPosition().intX() / Chunk.WIDTH;
			chunkZ = pl.getPosition().intZ() / Chunk.HEIGHT;

			for (int x = (chunkX - activationRadius); x <= (chunkX + activationRadius); x++)
				for (int z = (chunkZ - activationRadius); z <= (chunkZ + activationRadius); z++) {
					ChunkCoords key = new ChunkCoords(x, z);

					loadedChunks.add(key);
				}
		}
	}

	private void tickActiveChunks() {
		for (ChunkCoords coords : loadedChunks) {
			Chunk chunk = getChunkFromChunkCoords(coords.getX(), coords.getZ());

			int posX = coords.getX() * Chunk.WIDTH;
			int posZ = coords.getZ() * Chunk.HEIGHT;

			// In 3 rounds, picks up random block in a chunk and tick it,
			// Since Minecraft 1.8 this is customizable in attribute "randomTick" (or similar)
			for (int count = 0; count < 3; count++) {

				int randX = rand.nextInt(Chunk.WIDTH);
				int randY = rand.nextInt(Chunk.DEPTH);
				int randZ = rand.nextInt(Chunk.HEIGHT);

				Block block = Block.byId(chunk.getType(randX, randY, randZ));

				if (block.isTickingRandomly())
					block.onTick(this, posX + randX, randY, posZ + randZ);
			}
		}
	}

	private void advanceTime() {
		if (time++ >= 24000)
			time = 0;
	}

	public SafeUnorderedZoznam<EntityPlayer> getPlayersInWorld() {
		return entities.getAll(EntityPlayer.class);
	}

	public Chunk getChunkFromBlockCoords(int x, int z) {
		return getChunkFromChunkCoords(x >> 4, z >> 4);
	}

	public Chunk getChunkFromChunkCoords(int x, int z) {
		return chunkManager.getChunk(x, z);
	}

	public void forcePopulate(Chunk chunk) {
		chunkManager.populate(chunk);
	}

	public int getType(int x, int y, int z) {
		ensure(x, y, z);

		return getChunkFromBlockCoords(x, z).getType(x & 15, y, z & 15);
	}

	public void setType(int x, int y, int z, int type) {
		ensure(x, y, z);

		getChunkFromBlockCoords(x, z).setType(x & 15, y, z & 15, type);
		sendBlockChange(x, y, z, type, getMetadata(x, y, z));
	}

	public int getMetadata(int x, int y, int z) {
		ensure(x, y, z);

		return getChunkFromBlockCoords(x, z).getData(x & 15, y, z & 15);
	}

	public void setMetadata(int x, int y, int z, int data) {
		ensure(x, y, z);

		getChunkFromBlockCoords(x, z).setData(x & 15, y, z & 15, data);
		sendBlockChange(x, y, z, getType(x, y, z), data);
	}

	public void setTypeAndData(int x, int y, int z, int type, int data) {
		ensure(x, y, z);

		getChunkFromBlockCoords(x, z).setTypeAndData(x & 15, y, z & 15, type, data);
		sendBlockChange(x, y, z, type, data);
	}

	public void setAir(int x, int y, int z) {
		setTypeAndData(x, y, z, 0, 0);
	}

	private void ensure(int x, int y, int z) {
		Validate.isTrue(y >= 0 && y < 256, "Invalid coords x:" + x + " y: " + y + " z: " + z);
	}

	private Queue<Runnable> packetPendingTask = new LinkedList<>();
	
	private void sendBlockChange(int x, int y, int z, int type, int data) {
		packetPendingTask.add(new Runnable() {
			
			@Override
			public void run() {
				for (EntityPlayer player : getPlayersInWorld())
					player.getSession().sendPacket(new BlockChange(x, y, z, type, data));
			}
		});
	}

	public int getTerrainHeight(int x, int z) {
		return getChunkFromBlockCoords(x, z).getTerrainHeight(x & 15, z & 15);
	}

	public void setTime(int time) {
		this.time = time;

		for (EntityPlayer player : getPlayersInWorld())
			player.getSession().sendPacket(new TimeUpdate(time, time));
	}
}

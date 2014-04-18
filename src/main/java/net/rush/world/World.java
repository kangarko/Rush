package net.rush.world;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

import net.rush.chunk.Chunk;
import net.rush.chunk.ChunkCoords;
import net.rush.chunk.ChunkManager;
import net.rush.io.ChunkIoService;
import net.rush.model.Block;
import net.rush.model.Entity;
import net.rush.model.EntityManager;
import net.rush.model.LivingEntity;
import net.rush.model.Player;
import net.rush.model.Position;
import net.rush.model.entity.EntityRegistry;
import net.rush.model.misc.NextTickEntry;
import net.rush.model.misc.Vec3Pool;
import net.rush.packets.Packet;
import net.rush.packets.packet.TimeUpdatePacket;

import org.bukkit.Difficulty;
import org.bukkit.World.Environment;
import org.bukkit.WorldType;
import org.bukkit.entity.EntityType;

/**
 * A class which represents the in-game world.
 */
public class World {

	/**
	 * The number of pulses in a Minecraft day.
	 */
	private static final int PULSES_PER_DAY = 24000;

	/**
	 * The spawn position.
	 */
	private Position spawnPosition = new Position(0, 65, 0);

	/**
	 * The chunk manager.
	 */
	private final ChunkManager chunks;

	/**
	 * The entity manager.
	 */
	private final EntityManager entities = new EntityManager();


	private Queue<NextTickEntry> tickQueue = new LinkedList<NextTickEntry>();

	//private Set<NextTickEntry> pendingTickEntriesHashSet = new HashSet<NextTickEntry>();
	//private List<NextTickEntry> pendingTickEntries = new ArrayList<NextTickEntry>();
	//private TreeSet<NextTickEntry> pendingTickEntriesTreeSet = new TreeSet<NextTickEntry>();

	public Set<ChunkCoords> activeChunks = new HashSet<ChunkCoords>();
	protected int updateLCG = new Random().nextInt();

	private long time = 0;
	private int maxHeight = 256;
	public final Vec3Pool vectorPool = new Vec3Pool(300, 2000);
	public Random rand = new Random();

	/**
	 * Creates a new world with the specified chunk I/O service and world
	 * generator.
	 * 
	 * @param service
	 *            The chunk I/O service.
	 * @param generator
	 *            The world generator.
	 */
	public World(ChunkIoService service, WorldGenerator generator) {
		chunks = new ChunkManager(service, generator);
	}

	/**
	 * Updates all the entities within this world.
	 */
	public void pulse() {
		for (Entity entity : entities)
			entity.pulse();

		for (Entity entity : entities)
			entity.reset();

		advanceTime();
		resetActiveChunks();
		tickFromQueue();
		tickActiveChunks();
	}

	protected void resetActiveChunks() {
		activeChunks.clear();

		int chunkX, chunkZ;
		
		// how many chunks from player should be ticked (redstone activated, grass grown etc)
		// is 8 in notchian server
		int radius = 6;

		for (Player pl : getPlayers()) {
			chunkX = ((int) pl.getPosition().getX()) / Chunk.WIDTH;
			chunkZ = ((int) pl.getPosition().getZ()) / Chunk.HEIGHT;

			for (int x = (chunkX - radius); x <= (chunkX + radius); x++) {
				for (int z = (chunkZ - radius); z <= (chunkZ + radius); z++) {
					ChunkCoords key = new ChunkCoords(x, z);
					if (!activeChunks.contains(key))
						activeChunks.add(key);
				}
			}
		}
	}

	/**
	 * Gets the chunk manager.
	 * 
	 * @return The chunk manager.
	 */
	public ChunkManager getChunks() {
		return chunks;
	}

	/**
	 * Gets the entity manager.
	 * 
	 * @return The entity manager.
	 */
	public EntityManager getEntities() {
		return entities;
	}

	/**
	 * Gets a collection of all the players within this world.
	 * 
	 * @return A {@link Collection} of {@link Player} objects.
	 */
	public Collection<Player> getPlayers() {
		return entities.getAll(Player.class);
	}

	/**
	 * Gets the spawn position.
	 * 
	 * @return The spawn position.
	 */
	public Position getSpawnPosition() {
		return spawnPosition;
	}

	/**
	 * Gets the current time.
	 * 
	 * @return The current time.
	 */
	public long getTime() {
		return time;
	}

	/**
	 * Sets the current time.
	 * 
	 * @param time
	 *            The current time.
	 */
	public void setTime(long time) {
		this.time = time % PULSES_PER_DAY;

		TimeUpdatePacket msg = new TimeUpdatePacket(0, time); // TODO Correct world age?
		for (Player player : getPlayers())
			player.getSession().send(msg);
	}

	/**
	 * Advances the time forwards, should be called every pulse.
	 */
	private void advanceTime() {
		time = (time + 1) % PULSES_PER_DAY;
		// TODO: every now and again we should broadcast the time to all
		// players to keep things in sync
	}

	public Chunk getChunkAt(int x, int z) {
		return chunks.getChunk(x, z);
	}

	public void setTypeAndData(int x, int y, int z, int type, int data) {
		setTypeId(x, y, z, type);
		setBlockData(x, y, z, data);
	}

	public void setTypeId(int x, int y, int z, int type) {
		int chunkX = x / Chunk.WIDTH + ((x < 0 && x % Chunk.WIDTH != 0) ? -1 : 0);
		int chunkZ = z / Chunk.HEIGHT + ((z < 0 && z % Chunk.HEIGHT != 0) ? -1 : 0);

		int localX = (x - chunkX * Chunk.WIDTH) % Chunk.WIDTH;
		int localZ = (z - chunkZ * Chunk.HEIGHT) % Chunk.HEIGHT;

		Chunk chunk = chunks.getChunk(chunkX, chunkZ);
		chunk.setType(localX, localZ, y, type);
	}

	public void setBlockData(int x, int y, int z, int data) {
		int chunkX = x / Chunk.WIDTH + ((x < 0 && x % Chunk.WIDTH != 0) ? -1 : 0);
		int chunkZ = z / Chunk.HEIGHT + ((z < 0 && z % Chunk.HEIGHT != 0) ? -1 : 0);

		int localX = (x - chunkX * Chunk.WIDTH) % Chunk.WIDTH;
		int localZ = (z - chunkZ * Chunk.HEIGHT) % Chunk.HEIGHT;

		Chunk chunk = chunks.getChunk(chunkX, chunkZ);
		chunk.setMetaData(localX, localZ, y, data);
	}

	public int getTypeId(int x, int y, int z) {
		if (x >= -30000000 && z >= -30000000 && x < 30000000 && z < 30000000) {
			if (y < 0)
				return 0;
			else if (y >= 256)
				return 0;
			else {
				Chunk chunk = null;

				chunk = getChunkAt(x >> 4, z >> 4);
				return chunk.getType(x & 15, z & 15, y);
			}
		} else
			return 0;
	}

	public int getBlockData(int x, int y, int z) {
		int chunkX = x / Chunk.WIDTH + ((x < 0 && x % Chunk.WIDTH != 0) ? -1 : 0);
		int chunkZ = z / Chunk.HEIGHT + ((z < 0 && z % Chunk.HEIGHT != 0) ? -1 : 0);

		int localX = (x - chunkX * Chunk.WIDTH) % Chunk.WIDTH;
		int localZ = (z - chunkZ * Chunk.HEIGHT) % Chunk.HEIGHT;

		Chunk chunk = chunks.getChunk(chunkX, chunkZ);
		return chunk.getMetaData(localX, localZ, y);
	}

	public LivingEntity spawnEntity(Position pos, Class<? extends LivingEntity> clazz) {
		try {
			LivingEntity entity = clazz.getDeclaredConstructor(World.class).newInstance(this);

			entity.setPosition(pos);
			Packet smp = entity.createSpawnMessage();

			for(Player pl : getPlayers()) {
				pl.getSession().send(smp);
			}

			return entity;

		} catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException ex) {
			throw new Error("Error spawning entity" + clazz.getSimpleName(), ex);
		}
	}

	public LivingEntity spawnEntity(Position pos, EntityType type) {
		try {
			Class<? extends LivingEntity> clazz = EntityRegistry.entityLookup(type);
			LivingEntity entity = clazz.getDeclaredConstructor(World.class).newInstance(this);

			entity.setPosition(pos);
			Packet smp = entity.createSpawnMessage();

			for(Player pl : getPlayers()) {
				pl.getSession().send(smp);
			}

			return entity;

		} catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException ex) {
			throw new Error("Error spawning entity" + type.toString(), ex);
		}
	}

	// //

	public Difficulty getDifficulty() {
		return Difficulty.NORMAL;
	}

	public Environment getEnvironment() {
		return Environment.NORMAL;
	}

	public int getMaxHeight() {
		return maxHeight;
	}

	public String getName() {
		return "world";
	}

	public WorldType getWorldType() {
		return WorldType.NORMAL;
	}

	public void save() {
		try {
			this.chunks.saveAll();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public boolean setSpawnLocation(int x, int y, int z) {
		try {
			spawnPosition = new Position(x, y, z);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public int getBlockLightValue(int x, int y, int z) {
		return 0;
	}

	/**
	 * Runs through the list of updates to run and ticks them
	 * @return true if pending tick queue is empty
	 */
	public void tickFromQueue() {
		if(tickQueue.isEmpty())
			return;

		NextTickEntry nextTick = tickQueue.poll();

		if (chunks.chunkExist(nextTick.posX, nextTick.posZ)) {
			int id = getTypeId(nextTick.posX, nextTick.posY, nextTick.posZ);

			if (id > 0 && Block.isAssociatedWith(id, nextTick.blockId)) {
				Block.byId[id].tick(this, nextTick.posX, nextTick.posY, nextTick.posZ, rand);
			}
		} else {
			scheduleBlockUpdate(nextTick.posX, nextTick.posY, nextTick.posZ, nextTick.blockId, 0);
		}
	}

	public void scheduleBlockUpdate(int x, int y, int z, int blockID, int priority) {
		NextTickEntry tickEntry = new NextTickEntry(x, y, z, blockID);
		byte osem = 0;

		if (chunks.chunkExist(x - osem, y - osem, z - osem, x + osem, y + osem, z + osem)) {
			if (blockID > 0) {
				tickEntry.setScheduledTime(getTime());
				tickEntry.setPriority(priority);
			}

			if(!tickQueue.contains(tickEntry))
				tickQueue.add(tickEntry);
		}
	}

	protected void tickActiveChunks() {

		for(ChunkCoords coords : activeChunks) {
			//int xPos = coords.x * 16;
			//int zPos = coords.z * 16;
			Chunk chunk = getChunkAt(coords.x, coords.z);

			chunk.tickBlocks(this, rand);
			/*for(BlockPosition blockPos : chunk.getBlocks()) {
				if(blockPos.getBlock().getTickRandomly()) {
					int x = (int) blockPos.getPositon().getX();
					int y = (int) blockPos.getPositon().getY();
					int z = (int) blockPos.getPositon().getZ();
					if(rand.nextInt(1000) == 1)
						System.out.println("Tick @ " + x + ", " +y +", "+ z);
					blockPos.getBlock().tick(this, x, y, z, rand);
				}
			}*/

			/*int blockID;

			for (int index = 0; index < chunk.types.length; ++index) {
					for (int i = 0; i < 3; ++i) {
						updateLCG = updateLCG * 3 + 1013904223;
						blockID = updateLCG >> 2;
						int x = blockID & 15;
						int z = blockID >> 8 & 15;
						int y = blockID >> 16 & 15;
						int extendedId = getTypeId(x, y, z);

						if(extendedId <= 0)
							continue;

						Block blockId = Block.byId[extendedId];

						if (blockId != null && blockId.getTickRandomly()) {
							blockId.tick(this, x + xPos, y, z + zPos, rand);
						}
					}
			}*/

			/*for(int x = 0; x < Chunk.WIDTH; x++) {
				for(int y = 0; y < Chunk.DEPTH; y++) {
					for(int z = 0; z < Chunk.HEIGHT; z++) {

						int type = getTypeId(x, y, z);

						if(type > 0) {
							//if(type == Block.GRASS.id)
							//	System.out.println("grass @" + x + ", " + y + ", " + z + " is " + org.bukkit.Material.getMaterial(type));

							Block block = Block.byId[type];
							if (block != null && block.getTickRandomly())
								block.tick(this, x, y, z, rand);
						}
					}
				}
			}*/
		}
	}
}

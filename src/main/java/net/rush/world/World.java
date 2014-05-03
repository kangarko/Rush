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
import net.rush.model.ItemStack;
import net.rush.model.LivingEntity;
import net.rush.model.Player;
import net.rush.model.Position;
import net.rush.model.entity.EntityRegistry;
import net.rush.model.entity.ItemEntity;
import net.rush.model.misc.NextTickEntry;
import net.rush.model.misc.Vec3Pool;
import net.rush.packets.Packet;
import net.rush.packets.packet.BlockChangePacket;
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

	public Set<ChunkCoords> activeChunks = new HashSet<ChunkCoords>();

	private long time = 0;
	private int maxHeight = 256;
	public final Vec3Pool vectorPool = new Vec3Pool(300, 2000);
	public Random rand = new Random();

	protected int randomBlockChooser = rand.nextInt();

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
	public int pulse() {
		long now = System.currentTimeMillis();
		for (Entity entity : entities) {
			entity.pulse();
			entity.updateMetadata();
		}

		for (Entity entity : entities)
			entity.reset();

		advanceTime();
		resetActiveChunks();
		tickFromQueue();
		tickActiveChunks();
		return (int) (System.currentTimeMillis() - now);
	}

	protected void resetActiveChunks() {
		activeChunks.clear();

		int chunkX, chunkZ;

		// how many chunks from player should be ticked (redstone activated, grass grown etc)
		// is 7 in notchian server
		int activationRadius = 6;

		for (Player pl : getPlayers()) {
			chunkX = ((int) pl.getPosition().getX()) / Chunk.WIDTH;
			chunkZ = ((int) pl.getPosition().getZ()) / Chunk.HEIGHT;

			for (int x = (chunkX - activationRadius); x <= (chunkX + activationRadius); x++) {
				for (int z = (chunkZ - activationRadius); z <= (chunkZ + activationRadius); z++) {
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

	public Chunk getChunkFromBlockCoords(int i, int j) {
		return getChunkFromChunkCoords(i >> 4, j >> 4);
	}

	public Chunk getChunkFromChunkCoords(int x, int z) {
		return chunks.getChunk(x, z);
	}

	public void setAir(int x, int y, int z) {
		setTypeId(x, y, z, 0, true);
	}

	public boolean setTypeAndData(int x, int y, int z, int type, int data, boolean notifyPlayers) {
		setTypeId(x, y, z, type, notifyPlayers);
		setBlockData(x, y, z, data, notifyPlayers);
		return true;
	}

	/** @param notifyPlayers - should we send BlockChangePacket to all players in the world? */
	public void setTypeId(int x, int y, int z, int type, boolean notifyPlayers) {
		int chunkX = x / Chunk.WIDTH + ((x < 0 && x % Chunk.WIDTH != 0) ? -1 : 0);
		int chunkZ = z / Chunk.HEIGHT + ((z < 0 && z % Chunk.HEIGHT != 0) ? -1 : 0);

		int localX = (x - chunkX * Chunk.WIDTH) % Chunk.WIDTH;
		int localZ = (z - chunkZ * Chunk.HEIGHT) % Chunk.HEIGHT;

		Chunk chunk = chunks.getChunk(chunkX, chunkZ);
		chunk.setType(localX, localZ, y, type);

		if(notifyPlayers) 
			sendBlockChangePacket(x, y, z);
	}	

	public void setBlockData(int x, int y, int z, int data, boolean notifyPlayers) {
		int chunkX = x / Chunk.WIDTH + ((x < 0 && x % Chunk.WIDTH != 0) ? -1 : 0);
		int chunkZ = z / Chunk.HEIGHT + ((z < 0 && z % Chunk.HEIGHT != 0) ? -1 : 0);

		int localX = (x - chunkX * Chunk.WIDTH) % Chunk.WIDTH;
		int localZ = (z - chunkZ * Chunk.HEIGHT) % Chunk.HEIGHT;

		Chunk chunk = chunks.getChunk(chunkX, chunkZ);
		chunk.setMetaData(localX, localZ, y, data);

		if(notifyPlayers) 
			sendBlockChangePacket(x, y, z);
	}

	public void sendBlockChangePacket(int x, int y, int z) {
		BlockChangePacket packet = new BlockChangePacket(x, y, z, this);
		for(Player pl : getPlayers())
			pl.getSession().send(packet);
	}

	public int getTypeId(int x, int y, int z) {
		if (x >= -30000000 && z >= -30000000 && x < 30000000 && z < 30000000) {
			if (y < 0)
				return 0;
			else if (y >= 256)
				return 0;
			else {
				Chunk chunk = null;

				chunk = getChunkFromBlockCoords(x, z);
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

	public void dropItem(double x, double y, double z, int type, int count, int data) {
		ItemStack item = new ItemStack(type, count, data);
		float offset = 0.7F;
		double randX = rand.nextFloat() * offset + (1.0F - offset) * 0.5D;
		double randY = rand.nextFloat() * offset + (1.0F - offset) * 0.5D;
		double randZ = rand.nextFloat() * offset + (1.0F - offset) * 0.5D;
		ItemEntity entity = new ItemEntity(this, x + randX, y + randY, z + randZ, item);
		sendEntitySpawnPacket(entity);
		//entity.sendMetadataMessage();
	}

	public void dropItem(double x, double y, double z, int type) {
		dropItem(x, y, z, type, 1, 0);
	}

	public void sendEntitySpawnPacket(Entity en) {
		Packet spawn = en.createSpawnMessage();
		
		for(Player pl : getPlayers())
			pl.getSession().send(spawn);		
	}

	public LivingEntity spawnEntity(Position pos, EntityType type) {
		try {
			Class<? extends LivingEntity> clazz = EntityRegistry.entityLookup(type);
			LivingEntity entity = clazz.getDeclaredConstructor(World.class).newInstance(this);

			entity.setPosition(pos);
			Packet packet = entity.createSpawnMessage();

			for(Player pl : getPlayers()) {
				pl.getSession().send(packet);
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

	public void setSpawnLocation(int x, int y, int z) {
		spawnPosition = new Position(x, y, z);
	}

	public int getBlockLightValue(int x, int y, int z) {
		return 0;
	}

	public void playSound(double x, double y, double z, String soundName, float volume, float pitch) {
		if (soundName != null) 
			for (Player pl : getPlayers())
				pl.playSound(soundName, x, y, z, volume, pitch);
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
		byte osem = 0; // FIXME eight or zero?

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
			Chunk chunk = getChunkFromChunkCoords(coords.x, coords.z);

			int chunkX = coords.x * Chunk.WIDTH;
			int chunkZ = coords.z * Chunk.HEIGHT;

			// In 3 rounds, picks up random block in a chunk and tick it,
			// x y z is converted to world x y z
			// Since Minecraft 1.8 this is customizable in attribute "randomTick" (or similar)
			for(int count = 0; count < 3; count++) {

				// the rand.nextInt can be 0 and is always one number lower than the argument
				int x = rand.nextInt(16);
				int y = rand.nextInt(maxHeight);
				int z = rand.nextInt(16);

				int type = chunk.getType(x, z, y);

				if(type != 0)
					if(Block.byId[type].getTickRandomly())
						Block.byId[type].tick(this, x + chunkX + Chunk.WIDTH, y, z + chunkZ + Chunk.HEIGHT, rand);
			}

			// Ticks every block, laggy
			// code commented for later purposes
			/*for(int xx = chunkX; xx < chunkX + Chunk.WIDTH; xx++) {
				for(int zz = chunkZ; zz < chunkZ + Chunk.HEIGHT; zz++)	{		    	
					for(int yy = 0; yy < Chunk.DEPTH; yy++) {

						int type = getTypeId(xx, yy, zz);

						if(type == 0) 
							continue;

						Block block = Block.byId[type];

						if(block != null && block.getTickRandomly())
							block.tick(this, xx, yy, zz, rand);

					}
				}
			}*/
		}
	}
}

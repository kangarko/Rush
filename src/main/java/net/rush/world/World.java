package net.rush.world;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

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
import net.rush.model.misc.NextTickListEntry;
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


	private Set<NextTickListEntry> pendingTickListEntriesHashSet = new HashSet<NextTickListEntry>();
	private List<NextTickListEntry> pendingTickEntries = new ArrayList<NextTickListEntry>();
	private TreeSet<NextTickListEntry> pendingTickListEntriesTreeSet = new TreeSet<NextTickListEntry>();
	public Set<ChunkCoords> activeChunks = new HashSet<ChunkCoords>();
	protected int updateLCG = (new Random()).nextInt();
	
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
		tickWorld();
		tickBlocksAndAmbiance();
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

	public int getTypeId(int x, int y, int z) {
		return chunks.getChunk(x, z).getType(x, z, y);
	}

	public void setTypeId(int x, int y, int z, int type) {
		chunks.getChunk(x, z).setType(x, z, y, type);
	}

	public int getBlockDataAt(int x, int y, int z) {
		return chunks.getChunk(x, z).getMetaData(x, z, y);
	}

	public void setTypeAndData(int x, int y, int z, int type, int data) {
		chunks.getChunk(x, z).setType(x, z, y, type);
		chunks.getChunk(x, z).setMetaData(x, z, y, data);
	}

	public int getHighestBlockAt(int x, int z) {
		Chunk chunk = getChunkAt(x, z);
		int posY = maxHeight - 1;
		x &= 0xf;
		z &= 0xf;
		while (posY > 0) {
			int l = chunk.getType(x, posY, z);
			if (l == 0 || !Block.byId[l].blockMaterial.isSolid() || Block.byId[l].blockMaterial == net.rush.model.Material.LEAVES)
				posY--;
			else
				return posY + 1;
		}
		return -1;
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
	 */
	public boolean tickWorld() {
		int pendingEntries = pendingTickListEntriesTreeSet.size();

		if (pendingEntries != pendingTickListEntriesHashSet.size())
			throw new IllegalStateException("TickNextTick list out of synch");
		else {
			if (pendingEntries > 1000) {
				pendingEntries = 1000;
			}

			// cleaning
			NextTickListEntry nextTick;

			for (int i = 0; i < pendingEntries; ++i) {
				nextTick = pendingTickListEntriesTreeSet.first();

				if (nextTick.scheduledTime > getTime()) {
					break;
				}

				pendingTickListEntriesTreeSet.remove(nextTick);
				pendingTickListEntriesHashSet.remove(nextTick);
				pendingTickEntries.add(nextTick);
			}

			// ticking
			Iterator<NextTickListEntry> pendingIterator = pendingTickEntries.iterator();

			while (pendingIterator.hasNext()) {
				nextTick = (NextTickListEntry) pendingIterator.next();
				pendingIterator.remove();

				if (chunks.chunkExist(nextTick.xCoord, nextTick.zCoord)) {
					int id = getTypeId(nextTick.xCoord, nextTick.yCoord, nextTick.zCoord);

					if (id > 0 && Block.isAssociatedWith(id, nextTick.blockID)) {
						try {
							Block.byId[id].tick(this, nextTick.xCoord, nextTick.yCoord, nextTick.zCoord, rand);
						} catch (Throwable ex) {
							ex.printStackTrace();
						}
					}
				} else {
					scheduleBlockUpdate(nextTick.xCoord, nextTick.yCoord, nextTick.zCoord, nextTick.blockID, 0);
				}
			}

			pendingTickEntries.clear();
			return !pendingTickListEntriesTreeSet.isEmpty();
		}
	}

	public void scheduleBlockUpdate(int x, int y, int z, int blockID, int priority) {
		NextTickListEntry tickEntry = new NextTickListEntry(x, y, z, blockID);
		byte osem = 0;

		/*if (scheduledUpdatesAreImmediate && blockID > 0) {
			if (Block.byId[blockID].func_82506_l()) {
				osem = 8;

				if (chunks.chunkExist(var7.xCoord - osem, var7.yCoord - osem, var7.zCoord - osem, var7.xCoord + osem, var7.yCoord + osem, var7.zCoord + osem)) {
					int id = getBlockId(var7.xCoord, var7.yCoord, var7.zCoord);

					if (id == var7.blockID && id > 0) {
						Block.byId[id].tick(this, var7.xCoord, var7.yCoord, var7.zCoord, rand);
					}
				}

				return;
			}

			par5 = 1;
		}*/

		if (chunks.chunkExist(x - osem, y - osem, z - osem, x + osem, y + osem, z + osem)) {
			if (blockID > 0) {
				tickEntry.setScheduledTime(getTime());
				tickEntry.setPriority(priority);
			}

			if (!pendingTickListEntriesHashSet.contains(tickEntry)) {
				pendingTickListEntriesHashSet.add(tickEntry);
				pendingTickListEntriesTreeSet.add(tickEntry);
			}
		}
	}
	
	protected void tickBlocksAndAmbiance() {
		//super.tickBlocksAndAmbiance();
		Iterator<ChunkCoords> chunkSetIterator = activeChunks.iterator();

		while (chunkSetIterator.hasNext()) {
			ChunkCoords chunkCoords = chunkSetIterator.next();
			//int xPos = chunkCoords.x * 16;
			//int zPos = chunkCoords.z * 16;
			// get chunk
			Chunk chunk = getChunkAt(chunkCoords.x, chunkCoords.z);
			//moodSoundAndLightCheck(xPos, zPos, chunk);
			// tick chunk
			//chunk.updateSkylight();

			/*int var8;
			int var9;
			int index;
			int var11;*/

			/*if (rand.nextInt(100000) == 0 && isRaining() && isThundering()) {
				updateLCG = updateLCG * 3 + 1013904223;
				var8 = updateLCG >> 2;
				var9 = xPos + (var8 & 15);
				var10 = zPos + (var8 >> 8 & 15);
				var11 = getPrecipitationHeight(var9, var10);

				if (canLightningStrikeAt(var9, var11, var10)) {
					addWeatherEffect(new EntityLightningBolt(this, var9, var11, var10));
				}
			}*/

			// ice and snow
			//int blockID;

			/*if (rand.nextInt(16) == 0) {
				updateLCG = updateLCG * 3 + 1013904223;
				var8 = updateLCG >> 2;
				var9 = var8 & 15;
				var10 = var8 >> 8 & 15;
				var11 = getPrecipitationHeight(var9 + xPos, var10 + zPos);

				if (isBlockFreezableNaturally(var9 + xPos, var11 - 1, var10 + zPos)) {
					this.setBlock(var9 + xPos, var11 - 1, var10 + zPos, Block.ice.id);
				}

				if (isRaining() && canSnowAt(var9 + xPos, var11, var10 + zPos)) {
					this.setBlock(var9 + xPos, var11, var10 + zPos, Block.snow.id);
				}

				if (isRaining()) {
					BiomeGenBase genbase = getBiomeGenForCoords(var9 + xPos, var10 + zPos);

					if (genbase.canSpawnLightningBolt()) {
						blockID = getBlockId(var9 + xPos, var11 - 1, var10 + zPos);

						if (blockID != 0) {
							Block.byId[blockID].fillWithRain(this, var9 + xPos, var11 - 1, var10 + zPos);
						}
					}
				}
			}*/

			// ticking tiles
			for(int x = 0; x < 16; x++) {
				for(int y = 0; y < 256; y++) {
					for(int z = 0; z < 16; z++) {
						Block block = Block.byId[chunk.getType(x, z, y)];
						if(block.getTypeId() != Block.GRASS.blockID)
							return;
						if (block != null && block.getTickRandomly()) {
							block.tick(this, x, y, z, rand);
						}
					}
				}
			}
			/*ExtendedBlockStorage[] storage = chunk.getBlockStorageArray();
			var9 = storage.length;

			for (index = 0; index < var9; ++index) {
				ExtendedBlockStorage blockStorage = storage[index];

				if (blockStorage != null && blockStorage.getNeedsRandomTick()) {
					for (int i = 0; i < 3; ++i) {
						updateLCG = updateLCG * 3 + 1013904223;
						blockID = updateLCG >> 2;
						int x = blockID & 15;
						int z = blockID >> 8 & 15;
						int y = blockID >> 16 & 15;
						int extendedId = blockStorage.getExtBlockID(x, y, z);
						Block blockId = Block.byId[extendedId];

						if (blockId != null && blockId.getTickRandomly()) {
							blockId.tick(this, x + xPos, y + blockStorage.getYLocation(), z + zPos, rand);
						}
					}
				}
			}*/

		}
	}
}

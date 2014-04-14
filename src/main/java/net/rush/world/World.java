package net.rush.world;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import net.rush.chunk.Chunk;
import net.rush.chunk.ChunkManager;
import net.rush.io.ChunkIoService;
import net.rush.model.Entity;
import net.rush.model.EntityManager;
import net.rush.model.LivingEntity;
import net.rush.model.Player;
import net.rush.model.Position;
import net.rush.model.entity.EntityRegistry;
import net.rush.packets.Packet;
import net.rush.packets.packet.TimeUpdatePacket;

import org.bukkit.Difficulty;
import org.bukkit.Material;
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

	/**
	 * The current time.
	 */
	private long time = 0;
	
	public int worldYMax = 256;

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
	
	public int getTypeAt(int x, int y, int z) {
		return chunks.getChunk(x, z).getType(x, z, y);
	}
	
	public void setTypeId(int x, int y, int z, int type) {
		chunks.getChunk(x, z).setType(x, z, y, type);
	}
	
	public void setTypeAndData(int x, int y, int z, int type, int data) {
		chunks.getChunk(x, z).setType(x, z, y, type);
		chunks.getChunk(x, z).setMetaData(x, z, y, data);
	}
	
	@SuppressWarnings("deprecation") 
	public int getHighestBlockAt(int i, int j) {
		Chunk chunk = getChunkAt(i, j);
		int posY = worldYMax - 1;
		i &= 0xf;
		j &= 0xf;
		while (posY > 0) {
			int l = chunk.getType(i, posY, j);
			if (l == 0 || !Material.getMaterial(l).isSolid() /*!Block.blocksList[l].blockMaterial.getIsSolid() || Block.blocksList[l].blockMaterial == Material.leaves*/)
				posY--;
			else
				return posY + 1;
		}
		return -1;
	}
	
	@SuppressWarnings("deprecation") 
	public Material getBlockMaterial(int x, int y, int z) {
		return Material.getMaterial(getTypeAt(x, y, z));
	}
	
	public boolean isAirBlock(int x, int y, int z) {
		int type = 0;
		type = getTypeAt(x, y, z);
		return type == 0;
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
		return 256;
	}

	public String getName() {
		return "world";
	}

	public WorldType getWorldType() {
		return WorldType.NORMAL;
	}
	
	public long getSeed() {
		return 0; // FIXME
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

}

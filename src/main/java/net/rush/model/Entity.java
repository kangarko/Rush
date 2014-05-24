package net.rush.model;

import java.util.Random;

import net.rush.Server;
import net.rush.ServerProperties;
import net.rush.chunk.Chunk;
import net.rush.model.misc.AxisAlignedBB;
import net.rush.packets.Packet;
import net.rush.packets.packet.EntityMetadataPacket;
import net.rush.util.Parameter;
import net.rush.world.World;

import org.bukkit.entity.EntityType;

/**
 * Represents some entity in the world such as an item on the floor or a player.

 */
public abstract class Entity {

	/**
	 * The world this entity belongs to.
	 */
	protected final World world;

	/**
	 * The entity's metadata.
	 */
	protected final Parameter<?>[] metadata = new Parameter<?>[Parameter.METADATA_SIZE];
	
	/** If true, the metadata update update packet is sent in no time (0.041847 ms). */
	protected boolean metadataChanged = false;

	/**
	 * A flag indicating if this entity is currently active.
	 */
	protected boolean active = true;

	/**
	 * This entity's unique id.
	 */
	protected int id;

	/**
	 * The current position.
	 */
	protected Position position = Position.ZERO;

	/**
	 * The position in the last cycle.
	 */
	protected Position previousPosition = Position.ZERO;

	/**
	 * The current rotation.
	 */
	protected Rotation rotation = Rotation.ZERO;

	/**
	 * The rotation in the last cycle.
	 */
	protected Rotation previousRotation = Rotation.ZERO;

	protected EntityType entityType;
	
	protected long ticksLived = 0;
	protected int throwerId = 0;

	protected AxisAlignedBB boundingBox = AxisAlignedBB.EMPTY_BOUNDING_BOX;
	protected double motionX = 0;
	protected double motionY = 0;
	protected double motionZ = 0;

	// debug purposes
	protected boolean announced = false;
	
	protected Random rand = new Random();
	
	/**
	 * Creates an entity and adds it to the specified world.
	 * @param world The world.
	 */
	protected Entity(World world, EntityType entityType) {
		this.world = world;
		this.entityType = entityType;
		this.ticksLived = 0;
		//world.getEntities().allocate(this);

		setMetadata(new Parameter<Byte>(Parameter.TYPE_BYTE, 0, (byte) 0));
		setMetadata(new Parameter<Short>(Parameter.TYPE_SHORT, 1, (short) 300));
	}

	/**
	 * Checks if this entity is within the {@link ServerProperties#viewDistance} of
	 * another.
	 * @param other The other entity.
	 * @return {@code true} if the entities can see each other, {@code false} if
	 * not.
	 */
	public boolean isWithinDistance(Entity other) {
		if(position == null)
			throw new Error("Position of entity is null!");
		
		int distance = Server.getServer().getProperties().viewDistance > 10 ? 10 : Server.getServer().getProperties().viewDistance;
		
		double dx = Math.abs(position.x - other.position.x);
		double dz = Math.abs(position.z - other.position.z);
		return dx <= (distance * Chunk.WIDTH) && dz <= (distance * Chunk.HEIGHT);
	}

	/**
	 * Gets the world this entity is in.
	 * @return The world this entity is in.
	 */
	public World getWorld() {
		return world;
	}

	/**
	 * Destroys this entity by removing it from the world and marking it as not
	 * being active.
	 */
	public void destroy() {
		active = false;
		world.getEntities().deallocate(this);
	}

	/**
	 * Checks if this entity is active.
	 * @return {@code true} if so, {@code false} if not.
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Gets the id of this entity.
	 * @return The id.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Called every game cycle. Subclasses should implement this to implement
	 * periodic functionality e.g. mob AI.
	 */
	public void pulse() {}
	
	/**
	 * The reason this is not in pulse() is to prevent disabling that in case I forget to use super.pulse();
	 */
	public void updateEntity() {
		ticksLived++;
		
		if(metadataChanged) {
			EntityMetadataPacket message = new EntityMetadataPacket(id, metadata.clone());
			for (Player player : world.getPlayers()) {
				if (player != this) {
					player.getSession().send(message);
					player.sendMessage("&cRecieved metadata of " + entityType.toString() + " (id " + id + ") @ " + position.toString());
				}
			}
			
			if(!announced)
				System.out.println("Created Metadada Packet");			
				
			metadataChanged = false;
		}
	}

	/**
	 * Resets the previous position and rotations of the entity to the current
	 * position and rotation.
	 */
	public void reset() {
		previousPosition = position;
		previousRotation = rotation;
	}

	/**
	 * Gets this entity's position.
	 * @return The position of this entity.
	 */
	public Position getPosition() {
		if (position == null)
			throw new Error("Position of entity is null!");
		return position;
	}
	
	/**
	 * Gets the entity's previous position.
	 * @return The previous position of this entity.
	 */
	public Position getPreviousPosition() {
		return previousPosition;
	}

	/**
	 * Sets this entity's position.
	 * @param position The new position.
	 */
	public void setPosition(Position position) {
		this.position = position;
	}

	public void setPosition(double x, double y, double z) {
		this.position = new Position(x, y, z);
	}
	
	public void setX(double x) {
		this.position = new Position(x, position.y, position.z);
	}
	
	public void setY(double y) {
		this.position = new Position(position.x, y, position.z);
	}
	
	public void setZ(double z) {
		this.position = new Position(position.x, position.y, z);
	}

	/**
	 * Gets this entity's rotation.
	 * @return The rotation of this entity.
	 */
	public Rotation getRotation() {
		return rotation;
	}

	/**
	 * Gets the entity's previous rotation.
	 * @return The previous rotation of this entity.
	 */
	public Rotation getPreviousRotation() {
		return previousRotation;
	}

	/**
	 * Sets this entity's rotation.
	 * @param rotation The new rotation.
	 */
	public void setRotation(Rotation rotation) {
		this.rotation = rotation;
	}

	public void setRotation(double yaw, double pitch) {
		this.rotation = new Rotation(yaw, pitch);
	}
	
	public void onCollideWithPlayer(Player pl) {
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Entity other = (Entity) obj;
		if (id != other.id)
			return false;
		return true;
	}

	/**
	 * Creates a {@link Packet} which can be sent to a client to spawn this
	 * entity.
	 * @return A message which can spawn this entity.
	 */
	public abstract Packet createSpawnMessage();

	/**
	 * Creates a {@link Packet} which can be sent to a client to update this
	 * entity.
	 * @return A message which can update this entity.
	 */
	public abstract Packet createUpdateMessage();

	/**
	 * Checks if this entity has moved this cycle.
	 * @return {@code true} if so, {@code false} if not.
	 */
	public boolean hasMoved() {
		if(position == null)
			throw new Error("Position of entity is null!");

		return !position.equals(previousPosition);
	}

	/**
	 * Checks if this entity has rotated this cycle.
	 * @return {@code true} if so, {@code false} if not.
	 */
	public boolean hasRotated() {
		return !rotation.equals(previousRotation);
	}

	public EntityType getType() {
		return entityType;
	}

	public Parameter<?> getMetadata(int index) {
		return metadata[index];
	}

	public void setMetadata(Parameter<?> data) {
		setMetadata(data, true);
	}
	
	public void setMetadata(Parameter<?> data, boolean sendPackets) {
		metadata[data.getIndex()] = data;
		metadataChanged = sendPackets;
	}
	
	public Chunk getChunk() {
		return getWorld().getChunkFromBlockCoords((int)getPosition().x, (int)getPosition().z);
	}
}


package net.rush.model.entity;


import net.rush.api.world.Chunk;
import net.rush.model.RushChunk;
import net.rush.model.RushWorld;
import net.rush.protocol.Packet;
import net.rush.protocol.packets.EntityMetadata;
import net.rush.utils.MetaParam;

/**
 * Represents some entity in the world such as an item on the floor or a player.

 */
public abstract class RushEntity {

	protected final RushWorld world;
	protected final MetaParam<?>[] metadata = new MetaParam<?>[MetaParam.METADATA_SIZE];

	public boolean active = true;

	public int id;
	protected long ticksLived = 0;

	public double posX, posY, posZ, prevX, prevY, prevZ;

	protected boolean metadataChanged = false;

	/**
	 * Creates an entity and adds it to the specified world.
	 * @param world The world.
	 */
	protected RushEntity(RushWorld world) {
		this.world = world;

		// FIXME Unsure, notchian sends them.
		//setMetadata(new MetaParam<Byte>(0, (byte) 0));
		//setMetadata(new MetaParam<Short>(1, (short) 300));
		
		setMetadata(new MetaParam<Float>(6, 20F)); // Health.
	}

	public boolean canSee(RushEntity other) {
		int distance = 10; // TODO

		double dx = Math.abs(posX - other.posX);
		double dz = Math.abs(posZ - other.posZ);
		return dx <= (distance * RushChunk.WIDTH) && dz <= (distance * RushChunk.HEIGHT);
	}

	public void destroy() {
		active = false;
	}

	public void pulse() {		
		ticksLived++;

		if (metadataChanged) {
			metadataChanged = false;

			EntityMetadata message = new EntityMetadata(id, metadata);
			
			world.server.sessionRegistry.broadcastPacket(message); // TODO Include self?
		}
	}

	public void reset() {
		prevX = posX;
		prevY = posY;
		prevZ = posZ;
	}

	public void setPosition(double x, double y, double z) {
		posX = x;
		posY = y;
		posZ = z;
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
		if (prevX == posX && prevY == posY && prevZ == posZ)
			return false;

		return true;
	}

	public MetaParam<?> getMetadata(int index) {
		return metadata[index];
	}

	public void setMetadata(MetaParam<?> data) {
		metadata[data.getIndex()] = data;
		metadataChanged = true;
	}

	public Chunk getChunk() {
		return world.getChunkFromBlockCoords((int) posX, (int) posZ);
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
		RushEntity other = (RushEntity) obj;
		
		return id == other.id;
	}
	
	@Override
	public String toString() {
		return "id=" + id;
	}
}


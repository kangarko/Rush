package net.rush.model.entity;


import java.util.Objects;

import net.rush.api.Position;
import net.rush.api.meta.MetaParam;
import net.rush.api.world.Chunk;
import net.rush.model.RushChunk;
import net.rush.model.RushWorld;
import net.rush.protocol.Packet;
import net.rush.protocol.packets.EntityMetadata;

/**
 * Represents some entity in the world such as an item on the floor or a player.

 */
public abstract class RushEntity {

	public final RushWorld world;
	protected final MetaParam<?>[] metadata = new MetaParam<?>[MetaParam.METADATA_SIZE];
	
	protected long ticksLived = 0;
	private boolean active = true;
	public int id;

	public Position position = null;
	public Position prevPosition = Position.ZERO;

	protected boolean metadataChanged = false;

	/**
	 * Creates an entity and adds it to the specified world.
	 * @param world The world.
	 */
	protected RushEntity(RushWorld world) {
		this.world = world;

		// FIXME Unsure, notchian sends them.
		setMetadata(new MetaParam<Byte>(0, (byte) 0));
		setMetadata(new MetaParam<Short>(1, (short) 300));
		
		setMetadata(new MetaParam<Float>(6, 20F)); // Health.
		world.entities.allocate(this);
	}

	public boolean canSee(RushEntity other) {
		int distance = 10; // TODO

		double dx = Math.abs(position.x - other.position.x);
		double dz = Math.abs(position.z - other.position.z);
		return dx <= (distance * RushChunk.WIDTH) && dz <= (distance * RushChunk.HEIGHT);
	}

	public void pulse() {		
		ticksLived++;

		if (metadataChanged) {
			metadataChanged = false;
			world.server.sessionRegistry.broadcastPacket(new EntityMetadata(id, metadata)); // TODO Send to self?
		}
	}

	public void reset() {
		Objects.requireNonNull(position, "Position of " + this + " id " + id + " is null!");
		Objects.requireNonNull(prevPosition, "Previous position of " + this + " id " + id + " is null!");
		
		prevPosition = position;
	}

	public void setPosition(double x, double y, double z) {
		setPosition(new Position(x, y, z));
	}
	
	public void setPosition(Position position) {
		this.position = position;
	}

	public abstract Packet createSpawnMessage();
	public abstract Packet createUpdateMessage();

	public boolean hasMoved() {
		return !(position.equalsPosition(prevPosition));
	}
	
	public boolean hasRotated() {
		return !(position.equalsRotation(prevPosition));
	}

	public MetaParam<?> getMetadata(int index) {
		return metadata[index];
	}

	public void setMetadata(MetaParam<?> data) {
		metadata[data.getIndex()] = data;
		metadataChanged = true;
	}

	public Chunk getChunk() {
		return world.getChunkFromBlockCoords(position.intX(), position.intZ());
	}

	public boolean isActive() {
		return active;
	}
	
	public void destroy() {
		active = false;
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
		return getClass().getSimpleName();
	}
}


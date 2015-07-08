package net.rush.entity;


import java.util.Objects;

import org.apache.commons.lang3.Validate;

import lombok.Getter;
import net.rush.Server;
import net.rush.api.Position;
import net.rush.api.meta.MetaParam;
import net.rush.protocol.Packet;
import net.rush.protocol.packets.EntityMetadata;
import net.rush.world.World;
import net.rush.world.chunk.Chunk;

public abstract class Entity {

	@Getter
	protected final Server server;
	@Getter
	protected final World world;
	protected final MetaParam<?>[] metadata = new MetaParam<?>[MetaParam.METADATA_SIZE];
	
	@Getter
	protected Position position = null;
	protected Position prevPosition = Position.ZERO;	
	
	@Getter
	protected Integer id;
	protected long ticksLived = 0;
	protected boolean metadataChanged = false;	
	@Getter
	protected boolean active = true;

	/**
	 * Creates an entity and adds it to the specified world.
	 * @param world The world.
	 */
	protected Entity(World world) {
		this.server = world.getServer();
		this.world = world;
		
		world.getEntities().allocate(this);
		// FIXME Unsure, notchian sends them.
		setMetadata(new MetaParam<Byte>(0, (byte) 0));
		setMetadata(new MetaParam<Short>(1, (short) 300));
	}

	public abstract Packet createSpawnMessage();
	public abstract Packet createUpdateMessage();

	final void pulse() {
		if (!active) {
			world.getEntities().deallocate(this);
			return;
		}
		
		ticksLived++;

		if (metadataChanged) {
			metadataChanged = false;
			server.getSessionRegistry().broadcastPacketExcept(new EntityMetadata(id, metadata), id); // TODO Send to self?
		}
		
		onPulse();
	}

	public void onPulse() {
	}
	
	final void reset() {
		Objects.requireNonNull(position, "Position of " + this + id + " is null!");
		Objects.requireNonNull(prevPosition, "Previous position of " + this + id + " is null!");
		
		prevPosition = position;
	}
	
	public final void destroy() {
		active = false;
	}
	
	public final void assignId(int id) {
		Validate.isTrue(this.id == null, "Id already set on " + toString());
		
		this.id = id;
	}
	
	public final boolean canSee(Entity other) {
		int distance = 10; // TODO

		double dx = Math.abs(position.x - other.position.x);
		double dz = Math.abs(position.z - other.position.z);
		return dx <= (distance * Chunk.WIDTH) && dz <= (distance * Chunk.HEIGHT);
	}

	public final void setPosition(double x, double y, double z) {
		setPosition(new Position(x, y, z));
	}
	
	public final void setPosition(Position position) {
		this.position = position;
	}

	public final boolean hasMoved() {
		return !(position.equalsPosition(prevPosition));
	}
	
	public final boolean hasRotated() {
		return !(position.equalsRotation(prevPosition));
	}

	public final MetaParam<?> getMetadata(int index) {
		return metadata[index];
	}

	public final void setMetadata(MetaParam<?> data) {
		metadata[data.getIndex()] = data;
		metadataChanged = true;
	}

	public final Chunk getChunk() {
		return world.getChunkFromBlockCoords(position.intX(), position.intZ());
	}
	
	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public final boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		Entity other = (Entity) obj;
		
		return id == other.id;
	}
	
	@Override
	public String toString() {		
		return getClass().getSimpleName() + " id " + id;
	}
}


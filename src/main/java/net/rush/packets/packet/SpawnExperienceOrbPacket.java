package net.rush.packets.packet;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class SpawnExperienceOrbPacket extends Packet {
	@Serialize(type = Type.INT, order = 0)
	private final int entityId;
	@Serialize(type = Type.INT, order = 1)
	private final int x;
	@Serialize(type = Type.INT, order = 2)
	private final int y;
	@Serialize(type = Type.INT, order = 3)
	private final int z;
	@Serialize(type = Type.SHORT, order = 4)
	private final short count;

	public SpawnExperienceOrbPacket(int entityId, int x, int y, int z, short count) {
		super();
		this.entityId = entityId;
		this.x = x;
		this.y = y;
		this.z = z;
		this.count = count;
	}

	public int getOpcode() {
		return 0x1A;
	}

	public int getEntityId() {
		return entityId;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

	public short getCount() {
		return count;
	}

	public String getToStringDescription() {
		return String.format("entityId=\"%d\",x=\"%d\",y=\"%d\",z=\"%d\",count=\"%d\"", entityId, x, y, z, count);
	}
}

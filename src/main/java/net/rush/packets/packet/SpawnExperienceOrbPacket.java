package net.rush.packets.packet;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class SpawnExperienceOrbPacket extends Packet {
	public SpawnExperienceOrbPacket() {
		// TODO Auto-generated constructor stub
	}

	@Serialize(type = Type.INT, order = 0)
	private int entityId;
	@Serialize(type = Type.INT, order = 1)
	private int x;
	@Serialize(type = Type.INT, order = 2)
	private int y;
	@Serialize(type = Type.INT, order = 3)
	private int z;
	@Serialize(type = Type.SHORT, order = 4)
	private short count;

	public SpawnExperienceOrbPacket(int entityId, int x, int y, int z,
			short count) {
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
		return String.format(
				"entityId=\"%d\",x=\"%d\",y=\"%d\",z=\"%d\",count=\"%d\"",
				entityId, x, y, z, count);
	}

	@Override
	public void read18(ByteBufInputStream input) {
		// TODO Auto-generated method stub

	}

	@Override
	public void write18(ByteBufOutputStream output) {
		// TODO Auto-generated method stub

	}
}

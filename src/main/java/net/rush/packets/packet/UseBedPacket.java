package net.rush.packets.packet;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class UseBedPacket extends Packet {
	@Serialize(type = Type.INT, order = 0)
	private final int entityId;
	@Serialize(type = Type.BYTE, order = 1)
	private final byte unknown_byte_0;
	@Serialize(type = Type.INT, order = 2)
	private final int x;
	@Serialize(type = Type.BYTE, order = 3)
	private final byte y;
	@Serialize(type = Type.INT, order = 4)
	private final int z;

	public UseBedPacket(int entityId, byte unknown_byte_0, int x, byte y, int z) {
		super();
		this.entityId = entityId;
		this.unknown_byte_0 = unknown_byte_0;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public int getOpcode() {
		return 0x11;
	}

	public int getEntityId() {
		return entityId;
	}

	public byte getUnknown_byte_0() {
		return unknown_byte_0;
	}

	public int getX() {
		return x;
	}

	public byte getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

	public String getToStringDescription() {
		return String.format("entityId=\"%d\",unknown_byte_0=\"%d\",x=\"%d\",y=\"%d\",z=\"%d\"", entityId, unknown_byte_0, x, y, z);
	}
}

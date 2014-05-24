package net.rush.packets.packet;

import io.netty.buffer.ByteBufOutputStream;

import java.io.IOException;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class UseBedPacket extends Packet {
	
	public UseBedPacket() {
	}

	@Serialize(type = Type.INT, order = 0)
	private int entityId;
	@Serialize(type = Type.BYTE, order = 1)
	private byte unknown_byte_0;
	@Serialize(type = Type.INT, order = 2)
	private int x;
	@Serialize(type = Type.BYTE, order = 3)
	private byte y;
	@Serialize(type = Type.INT, order = 4)
	private int z;

	public UseBedPacket(int entityId, int x, byte y, int z) {
		super();
		this.entityId = entityId;
		this.unknown_byte_0 = 0;
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
	
	@Override
	public void write17(ByteBufOutputStream output) throws IOException {
		output.writeInt(entityId);
		output.writeInt(x);
		output.writeByte(y);
		output.writeInt(z);
	}
}

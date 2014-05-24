package net.rush.packets.packet;

import io.netty.buffer.ByteBufOutputStream;

import java.io.IOException;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class ThunderboltPacket extends Packet {
	
	public ThunderboltPacket() {
	}

	@Serialize(type = Type.INT, order = 0)
	private int entityId;
	@Serialize(type = Type.BYTE, order = 1)
	private byte thunderBoltId;
	@Serialize(type = Type.INT, order = 2)
	private int x;
	@Serialize(type = Type.INT, order = 3)
	private int y;
	@Serialize(type = Type.INT, order = 4)
	private int z;

	public ThunderboltPacket(int entityId, int x, int y, int z) {
		super();
		this.entityId = entityId;
		this.thunderBoltId = 1;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public int getOpcode() {
		return 0x47;
	}

	public int getEntityId() {
		return entityId;
	}

	public byte getThunderBoltId() {
		return thunderBoltId;
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

	public String getToStringDescription() {
		return String.format("entityId=\"%d\",unknown_byte_0=\"%d\",x=\"%d\",y=\"%d\",z=\"%d\"", entityId, thunderBoltId, x, y, z);
	}

	@Override
	public void write17(ByteBufOutputStream output) throws IOException {
		writeVarInt(entityId, output);
		output.writeByte(thunderBoltId);
		output.writeInt(x * 32);
		output.writeInt(y * 32);
		output.writeInt(z * 32);
	}
}

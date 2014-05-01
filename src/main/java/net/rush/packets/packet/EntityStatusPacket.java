package net.rush.packets.packet;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

import java.io.IOException;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class EntityStatusPacket extends Packet {

	public EntityStatusPacket() {
	}

	@Serialize(type = Type.INT, order = 0)
	private int entityId;
	@Serialize(type = Type.BYTE, order = 1)
	private byte status;

	public EntityStatusPacket(int entityId, byte status) {
		super();
		this.entityId = entityId;
		this.status = status;
	}

	public int getOpcode() {
		return 0x26;
	}

	public int getEntityId() {
		return entityId;
	}

	public byte getStatus() {
		return status;
	}

	public String getToStringDescription() {
		return String.format("entityId=\"%d\",status=\"%d\"", entityId, status);
	}

	@Override
	public void read17(ByteBufInputStream input) throws IOException {
		entityId = input.readInt();
		status = input.readByte();
	}

	@Override
	public void write17(ByteBufOutputStream output) throws IOException {
		output.writeInt(entityId);
		output.writeByte(status);
	}
}

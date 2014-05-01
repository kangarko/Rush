package net.rush.packets.packet;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

import java.io.IOException;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class EntityExistsPacket extends Packet {

	public EntityExistsPacket() {
	}

	@Serialize(type = Type.INT, order = 0)
	private int entityId;

	public EntityExistsPacket(int entityId) {
		super();
		this.entityId = entityId;
	}

	public int getOpcode() {
		return 0x1E;
	}

	public int getEntityId() {
		return entityId;
	}

	public String getToStringDescription() {
		return String.format("entityId=\"%d\"", entityId);
	}

	@Override
	public void read17(ByteBufInputStream input) throws IOException {
		entityId = input.readInt();
	}

	@Override
	public void write17(ByteBufOutputStream output) throws IOException {
		output.writeInt(entityId);
	}
}

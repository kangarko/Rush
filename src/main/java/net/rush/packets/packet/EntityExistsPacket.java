package net.rush.packets.packet;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class EntityExistsPacket extends Packet {
	public EntityExistsPacket() {
		// TODO Auto-generated constructor stub
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
	public void read18(ByteBufInputStream input) {
		// TODO Auto-generated method stub

	}

	@Override
	public void write18(ByteBufOutputStream output) {
		// TODO Auto-generated method stub

	}
}

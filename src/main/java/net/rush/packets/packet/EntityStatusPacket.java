package net.rush.packets.packet;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class EntityStatusPacket extends Packet {
	@Serialize(type = Type.INT, order = 0)
	private final int entityId;
	@Serialize(type = Type.BYTE, order = 1)
	private final byte status;

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
}

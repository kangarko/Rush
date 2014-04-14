package net.rush.packets.packet;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class EntityExistsPacket extends Packet {
	@Serialize(type = Type.INT, order = 0)
	private final int entityId;

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
}

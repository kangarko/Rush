package net.rush.packets.packet;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class EntityHeadLookPacket extends Packet {
	@Serialize(type = Type.INT, order = 0)
	private final int entityId;
	@Serialize(type = Type.BYTE, order = 1)
	private final byte headYaw;

	public EntityHeadLookPacket(int entityId, byte headYaw) {
		super();
		this.entityId = entityId;
		this.headYaw = headYaw;
	}

	public int getOpcode() {
		return 0x23;
	}

	public int getEntityId() {
		return entityId;
	}

	public byte getHeadYaw() {
		return headYaw;
	}

	public String getToStringDescription() {
		return String.format("entityId=\"%d\",headYaw=\"%d\"", entityId, headYaw);
	}
}

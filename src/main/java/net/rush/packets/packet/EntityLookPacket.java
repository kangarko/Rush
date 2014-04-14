package net.rush.packets.packet;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class EntityLookPacket extends Packet {
	@Serialize(type = Type.INT, order = 0)
	private final int entityId;
	@Serialize(type = Type.BYTE, order = 1)
	private final byte yaw;
	@Serialize(type = Type.BYTE, order = 2)
	private final byte pitch;

	public EntityLookPacket(int entityId, byte yaw, byte pitch) {
		super();
		this.entityId = entityId;
		this.yaw = yaw;
		this.pitch = pitch;
	}

	public int getOpcode() {
		return 0x20;
	}

	public int getEntityId() {
		return entityId;
	}

	public byte getYaw() {
		return yaw;
	}

	public byte getPitch() {
		return pitch;
	}

	public String getToStringDescription() {
		return String.format("entityId=\"%d\",yaw=\"%d\",pitch=\"%d\"", entityId, yaw, pitch);
	}
}

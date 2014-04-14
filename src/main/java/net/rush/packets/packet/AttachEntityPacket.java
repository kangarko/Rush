package net.rush.packets.packet;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class AttachEntityPacket extends Packet {
	@Serialize(type = Type.INT, order = 0)
	private final int entityId;
	@Serialize(type = Type.INT, order = 1)
	private final int vehicleId;
	@Serialize(type = Type.UNSIGNED_BYTE, order = 2)
	private final byte leash;

	public AttachEntityPacket(int entityId, int vehicleId, boolean leashed) {
		super();
		this.entityId = entityId;
		this.vehicleId = vehicleId;
		leash = (byte) (leashed ? 1 : 0);
	}

	public int getOpcode() {
		return 0x27;
	}

	public int getEntityId() {
		return entityId;
	}

	public int getVehicleId() {
		return vehicleId;
	}

	public byte getLeash() {
		return leash;
	}

	public String getToStringDescription() {
		return String.format("entityId=\"%d\",vehicleId=\"%d\"", entityId, vehicleId);
	}
}

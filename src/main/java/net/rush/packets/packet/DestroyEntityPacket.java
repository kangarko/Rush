package net.rush.packets.packet;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class DestroyEntityPacket extends Packet {
	@Serialize(type = Type.BYTE, order = 0)
	private final byte entityCount;
	@Serialize(type = Type.INT_ARRAY, order = 1)
	private final int[] entityIDs;

	public DestroyEntityPacket(int[] entityIDs) {
		super();
		entityCount = (byte) entityIDs.length;
		this.entityIDs = entityIDs;
	}

	public int getOpcode() {
		return 0x1D;
	}

	public byte getEntityCount() {
		return entityCount;
	}

	public int[] getEntityIDs() {
		return entityIDs;
	}

	public String getToStringDescription() {
		return String.format("entityCount=\"%d\", entityIDs=\"%c\"", entityCount, entityIDs);
	}
}

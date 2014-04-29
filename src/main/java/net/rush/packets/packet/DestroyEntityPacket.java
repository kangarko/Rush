package net.rush.packets.packet;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class DestroyEntityPacket extends Packet {
	public DestroyEntityPacket() {
		// TODO Auto-generated constructor stub
	}

	@Serialize(type = Type.BYTE, order = 0)
	private byte entityCount;
	@Serialize(type = Type.INT_ARRAY, order = 1)
	private int[] entityIDs;

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
		return String.format("entityCount=\"%d\", entityIDs=\"%c\"",
				entityCount, entityIDs);
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

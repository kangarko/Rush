package net.rush.packets.packet;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

import java.io.IOException;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class DestroyEntityPacket extends Packet {
	
	public DestroyEntityPacket() {
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
		return String.format("entityCount=\"%d\", entityIDs=\"%c\"", entityCount, entityIDs);
	}
	
	@Override
	public void read17(ByteBufInputStream input) throws IOException {
		entityCount = input.readByte();
		//entityIDs = new int[entityCount];

        for (int i = 0; i < entityCount; ++i) {
            entityIDs[i] = input.readInt();
        }
	}
	
	@Override
	public void write17(ByteBufOutputStream output) throws IOException {
		output.writeByte(entityCount);
		for (int i = 0; i < entityCount; i++) {
			output.write(entityIDs[i]);
		}
	}

}

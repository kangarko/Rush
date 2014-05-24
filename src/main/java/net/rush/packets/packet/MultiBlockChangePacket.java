package net.rush.packets.packet;

import io.netty.buffer.ByteBufOutputStream;

import java.io.IOException;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class MultiBlockChangePacket extends Packet {
	
	public MultiBlockChangePacket() {
	}

	@Serialize(type = Type.INT, order = 0)
	private int chunkX;
	@Serialize(type = Type.INT, order = 1)
	private int chunkZ;
	@Serialize(type = Type.SHORT, order = 2)
	private short recordCount;
	@Serialize(type = Type.INT, order = 3)
	private int dataSize;
	@Serialize(type = Type.BYTE_ARRAY, order = 4, moreInfo = 3)
	private byte[] data;

	public MultiBlockChangePacket(int chunkX, int chunkZ, short recordCount, int dataSize, byte[] data) {
		super();
		this.chunkX = chunkX;
		this.chunkZ = chunkZ;
		this.recordCount = recordCount;
		this.dataSize = dataSize;
		this.data = data;
	}

	public int getOpcode() {
		return 0x34;
	}

	public int getChunkX() {
		return chunkX;
	}

	public int getChunkZ() {
		return chunkZ;
	}

	public short getRecordCount() {
		return recordCount;
	}

	public int getDataSize() {
		return dataSize;
	}

	public byte[] getData() {
		return data;
	}

	public String getToStringDescription() {
		return String.format("chunkX=\"%d\",chunkZ=\"%d\",recordCount=\"%d\",dataSize=\"%d\",data=byte[%d]", chunkX, chunkZ, recordCount, dataSize, data.length);
	}
	
	@Override
	public void write17(ByteBufOutputStream output) throws IOException {
		output.writeInt(chunkX);
		output.writeInt(chunkZ);
		output.writeShort(recordCount);
		output.writeInt(dataSize);
		output.write(data);
	}

}

package net.rush.packets.packet;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class MultiBlockChangePacket extends Packet {
	@Serialize(type = Type.INT, order = 0)
	private final int chunkX;
	@Serialize(type = Type.INT, order = 1)
	private final int chunkZ;
	@Serialize(type = Type.SHORT, order = 2)
	private final short recordCount;
	@Serialize(type = Type.INT, order = 3)
	private final int dataSize;
	@Serialize(type = Type.BYTE_ARRAY, order = 4, moreInfo = 3)
	private final byte[] data;

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
}

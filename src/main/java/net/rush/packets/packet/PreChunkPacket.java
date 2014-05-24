package net.rush.packets.packet;

import io.netty.buffer.ByteBufOutputStream;

import java.io.IOException;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class PreChunkPacket extends Packet {
	
	public PreChunkPacket() {
	}

	@Serialize(type = Type.SHORT, order = 0)
	private short chunkCount;
	@Serialize(type = Type.INT, order = 1)
	private int dataLength;
	@Serialize(type = Type.BOOL, order = 2)
	private boolean skyLight;
	@Serialize(type = Type.BYTE_ARRAY, order = 3)
	private byte[] data;
	// Meta information
	@Serialize(type = Type.INT, order = 4)
	private int x;
	@Serialize(type = Type.INT, order = 5)
	private int z;
	@Serialize(type = Type.UNSIGNED_SHORT, order = 6)
	private int primaryBitMap;
	@Serialize(type = Type.UNSIGNED_SHORT, order = 7)
	private int addBitMap;

	public PreChunkPacket(short chunkCount, int dataLength, boolean skyLight, byte[] data, int x, int z, int primaryBitMap, int addBitMap) {
		super();
		this.chunkCount = chunkCount;
		this.dataLength = dataLength;
		this.skyLight = skyLight;
		this.data = data;
		this.x = x;
		this.z = z;
		this.primaryBitMap = primaryBitMap;
		this.addBitMap = addBitMap;
	}

	public int getOpcode() {
		return 0x32;
	}

	public short getChunkCount() {
		return chunkCount;
	}

	public int getDataLength() {
		return dataLength;
	}

	public boolean getSkyLight() {
		return skyLight;
	}

	public byte[] getData() {
		return data;
	}

	public int getX() {
		return x;
	}

	public int getZ() {
		return z;
	}

	public int getPrimaryBitMap() {
		return primaryBitMap;
	}

	public int getAddBitMap() {
		return addBitMap;
	}

	public String getToStringDescription() {
		return String.format("x=\"%a,%b,%c,x=%d,z=%e,%f,%g", chunkCount, dataLength, data, x, z, primaryBitMap, addBitMap);
	}
	
	@Override
	public void write17(ByteBufOutputStream output) throws IOException {
		output.writeShort(chunkCount);
		output.writeInt(dataLength);
		output.writeBoolean(skyLight);
		output.write(data);
		//meta
		output.writeInt(x);
		output.writeInt(z);
		output.writeShort(primaryBitMap);
		output.writeShort(addBitMap);
	}

}

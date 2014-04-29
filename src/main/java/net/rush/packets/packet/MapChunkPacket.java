package net.rush.packets.packet;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class MapChunkPacket extends Packet {
	public MapChunkPacket() {
		// TODO Auto-generated constructor stub
	}

	@Serialize(type = Type.INT, order = 0)
	private int x;
	@Serialize(type = Type.INT, order = 1)
	private int z;
	@Serialize(type = Type.BOOL, order = 2)
	private boolean groundUpContinuous;
	@Serialize(type = Type.UNSIGNED_SHORT, order = 3)
	private int primaryBitMap;
	@Serialize(type = Type.UNSIGNED_SHORT, order = 4)
	private int addBitMap;
	@Serialize(type = Type.INT, order = 5)
	private int compressedSize;
	@Serialize(type = Type.BYTE_ARRAY, order = 6, moreInfo = 5)
	private byte[] compressedChunkData;

	public MapChunkPacket(int x, int z, boolean groundUpContinuous,
			int primaryBitMap, int addBitMap, byte[] chunkData) {
		this(x, z, groundUpContinuous, primaryBitMap, addBitMap,
				chunkData.length, chunkData);
	}

	public MapChunkPacket(int x, int z, boolean groundUpContinuous,
			int primaryBitMap, int addBitMap, int compressedSize,
			byte[] chunkData) {
		super();
		this.x = x;
		this.z = z;
		this.groundUpContinuous = groundUpContinuous;
		this.primaryBitMap = primaryBitMap;
		this.addBitMap = addBitMap;
		this.compressedSize = compressedSize;
		compressedChunkData = chunkData;
	}

	public int getOpcode() {
		return 0x33;
	}

	public int getX() {
		return x;
	}

	public int getZ() {
		return z;
	}

	public boolean getGroundUpContinuous() {
		return groundUpContinuous;
	}

	public int getPrimaryBitMap() {
		return primaryBitMap;
	}

	public int getAddBitMap() {
		return addBitMap;
	}

	public int getCompressedSize() {
		return compressedSize;
	}

	public byte[] getCompressedChunkData() {
		return compressedChunkData;
	}

	public String getToStringDescription() {
		return String
				.format("x=\"%d\",z=\"%d\",groundUpContinuous=\"%b\",primaryBitMap=\"%d\",addBitMap=\"%d\",compressedSize=\"%d\",chunkData=byte[%d]",
						x, z, groundUpContinuous, primaryBitMap, addBitMap,
						compressedSize, compressedChunkData.length);
	}

	@Override
	public void read17(ByteBufInputStream input) {
		// TODO Auto-generated method stub

	}

	@Override
	public void write17(ByteBufOutputStream output) {
		// TODO Auto-generated method stub

	}
}

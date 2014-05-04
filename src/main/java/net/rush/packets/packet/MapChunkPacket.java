package net.rush.packets.packet;

import io.netty.buffer.ByteBufOutputStream;

import java.io.IOException;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class MapChunkPacket extends Packet {

	public MapChunkPacket() {
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

	public MapChunkPacket(int x, int z, boolean groundUpContinuous, int primaryBitMap, int addBitMap, byte[] chunkData) {
		this(x, z, groundUpContinuous, primaryBitMap, addBitMap, chunkData.length, chunkData);
	}

	public MapChunkPacket(int x, int z, boolean groundUpContinuous, int primaryBitMap, int addBitMap, int compressedSize, byte[] chunkData) {
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
		return String.format("x=\"%d\",z=\"%d\",groundUpContinuous=\"%b\",primaryBitMap=\"%d\",addBitMap=\"%d\",compressedSize=\"%d\",chunkData=byte[%d]", x, z, groundUpContinuous, primaryBitMap, addBitMap, compressedSize,
				compressedChunkData.length);
	}

	/*@Override
	public void read17(ByteBufInputStream input) throws IOException {
		x = input.readInt();
		z = input.readInt();
		groundUpContinuous = input.readBoolean();
		primaryBitMap = input.readUnsignedShort();
		addBitMap = input.readUnsignedShort();
		compressedSize = input.readInt();

		byte[] bytes = new byte[5];
		input.readFully(bytes);

		compressedChunkData = bytes;

		/*if (buildBuffer.length < compressedSize) {
		    buildBuffer = new byte[compressedSize];
		}

		input.readFully(buildBuffer, 0, compressedSize);
		int i = 0;

		int j;

		for (j = 0; j < 16; ++j) {
		    i += this.primaryBitMap >> j & 1;
		}

		j = 12288 * i;
		if (this.groundUpContinuous) {
		    j += 256;
		}

		this.compressedChunkData = new byte[j];
		Inflater inflater = new Inflater();

		inflater.setInput(buildBuffer, 0, compressedSize);

		try {
		    inflater.inflate(this.compressedChunkData);
		} catch (DataFormatException dataformatexception) {
		    throw new IOException("Bad compressed data format");
		} finally {
		    inflater.end();
		}*/
	//}

	@Override
	public void write17(ByteBufOutputStream output) throws IOException {
		output.writeInt(x);
		output.writeInt(z);
		output.writeBoolean(groundUpContinuous);
		output.writeShort(primaryBitMap);
		output.writeShort(addBitMap);
		//output.writeShort((short) (this.primaryBitMap & '\uffff'));
		//output.writeShort((short) (this.addBitMap & '\uffff'));
		output.writeInt(compressedSize);
		output.write(compressedChunkData);
		//output.write(compressedChunkData, 0, compressedSize);
	}
}

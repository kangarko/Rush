package net.rush.packets.packet;

import io.netty.buffer.ByteBufOutputStream;

import java.io.IOException;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;
import net.rush.world.World;

public class BlockChangePacket extends Packet {

	public BlockChangePacket() {
	}

	@Serialize(type = Type.INT, order = 0)
	private int x;
	@Serialize(type = Type.BYTE, order = 1)
	private byte y;
	@Serialize(type = Type.INT, order = 2)
	private int z;
	@Serialize(type = Type.SHORT, order = 3)
	private short blockType;
	@Serialize(type = Type.BYTE, order = 4)
	private byte blockMetadata;

	public BlockChangePacket(int x, int y, int z, World world) {
		this(x, y, z, world.getTypeId(x, y, z), world.getBlockData(x, y, z));
	}

	public BlockChangePacket(int x, int y, int z, int typeId, int data) {
		super();
		this.x = x;
		this.y = (byte) y;
		this.z = z;
		blockType = (short) typeId;
		blockMetadata = (byte) data;
	}

	public int getOpcode() {
		return 0x35;
	}

	public int getX() {
		return x;
	}

	public byte getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

	public short getBlockType() {
		return blockType;
	}

	public byte getBlockMetadata() {
		return blockMetadata;
	}

	public String getToStringDescription() {
		return String.format("x=\"%d\",y=\"%d\",z=\"%d\",blockType=\"%d\",blockMetadata=\"%d\"", x, y, z, blockType, blockMetadata);
	}

	@Override
	public void write17(ByteBufOutputStream output) throws IOException {
		output.writeInt(x);
		output.writeByte(y);
		output.writeInt(z);
		writeVarInt(blockType, output);
		output.writeByte(blockMetadata);
	}
}

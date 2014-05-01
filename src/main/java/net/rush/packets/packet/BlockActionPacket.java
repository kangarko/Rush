package net.rush.packets.packet;

import io.netty.buffer.ByteBufOutputStream;

import java.io.IOException;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class BlockActionPacket extends Packet {
	public BlockActionPacket() {
	}

	@Serialize(type = Type.INT, order = 0)
	private int x;
	@Serialize(type = Type.SHORT, order = 1)
	private short y;
	@Serialize(type = Type.INT, order = 2)
	private int z;
	@Serialize(type = Type.BYTE, order = 3)
	private byte byte1;
	@Serialize(type = Type.BYTE, order = 4)
	private byte byte2;
	@Serialize(type = Type.SHORT, order = 5)
	private short blockId;

	public BlockActionPacket(int x, short y, int z, byte byte1, byte byte2, short blockId) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		this.byte1 = byte1;
		this.byte2 = byte2;
		this.blockId = blockId;
	}

	public int getOpcode() {
		return 0x36;
	}

	public int getX() {
		return x;
	}

	public short getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

	public byte getByte1() {
		return byte1;
	}

	public byte getByte2() {
		return byte2;
	}

	public short getBlockId() {
		return blockId;
	}

	public String getToStringDescription() {
		return String.format("x=\"%d\",y=\"%d\",z=\"%d\",byte1=\"%d\",byte2=\"%d\", blockId=\"%d\"", x, y, z, byte1, byte2, blockId);
	}

	@Override
	public void write17(ByteBufOutputStream output) throws IOException {
		output.writeInt(x);
		output.writeShort(y);
		output.writeInt(z);
		output.writeByte(byte1);
		output.writeByte(byte2);
		writeVarInt(blockId, output);
	}
}

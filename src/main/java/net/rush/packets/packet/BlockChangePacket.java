package net.rush.packets.packet;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;
import net.rush.world.World;

public class BlockChangePacket extends Packet {
	@Serialize(type = Type.INT, order = 0)
	private final int x;
	@Serialize(type = Type.BYTE, order = 1)
	private final byte y;
	@Serialize(type = Type.INT, order = 2)
	private final int z;
	@Serialize(type = Type.SHORT, order = 3)
	private final short blockType;
	@Serialize(type = Type.BYTE, order = 4)
	private final byte blockMetadata;

	public BlockChangePacket(int x, int y, int z, World world) {
		super();
		this.x = x;
		this.y = (byte)y;
		this.z = z;
		this.blockType = (short) world.getTypeId(x, y, z);
		this.blockMetadata = (byte) world.getBlockData(x, y, z);
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
}

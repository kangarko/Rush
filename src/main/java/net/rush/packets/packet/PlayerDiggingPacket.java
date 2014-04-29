package net.rush.packets.packet;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class PlayerDiggingPacket extends Packet {

	public PlayerDiggingPacket() {
		// TODO Auto-generated constructor stub
	}

	public static final int STATE_START_DIGGING = 0;
	public static final int STATE_DONE_DIGGING = 2;
	public static final int STATE_DROP_ITEM = 4;

	@Serialize(type = Type.BYTE, order = 0)
	private byte status;
	@Serialize(type = Type.INT, order = 1)
	private int x;
	@Serialize(type = Type.BYTE, order = 2)
	private int y;
	@Serialize(type = Type.INT, order = 3)
	private int z;
	@Serialize(type = Type.BYTE, order = 4)
	private byte face;

	public PlayerDiggingPacket(byte status, int x, byte y, int z, byte face) {
		super();
		this.status = status;
		this.x = x;
		this.y = y;
		this.z = z;
		this.face = face;
	}

	public int getOpcode() {
		return 0x0E;
	}

	public byte getStatus() {
		return status;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

	public byte getFace() {
		return face;
	}

	public String getToStringDescription() {
		return String.format(
				"status=\"%d\",x=\"%d\",y=\"%d\",z=\"%d\",face=\"%d\"", status,
				x, y, z, face);
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

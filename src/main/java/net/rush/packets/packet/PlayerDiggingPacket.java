package net.rush.packets.packet;

import io.netty.buffer.ByteBufInputStream;

import java.io.IOException;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class PlayerDiggingPacket extends Packet {

	public PlayerDiggingPacket() {
	}

	public static final int START_DIGGING = 0;
	public static final int CANCEL_DIGGING = 1;
	public static final int DONE_DIGGING = 2;
	public static final int DROP_ITEMSTACK = 3;
	public static final int DROP_ITEM = 4;
	public static final int SHOOT_OR_EAT = 5;

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
		return String.format("status=\"%d\",x=\"%d\",y=\"%d\",z=\"%d\",face=\"%d\"", status, x, y, z, face);
	}
	
	@Override
	public void read17(ByteBufInputStream input) throws IOException {
		status = input.readByte();
		x = input.readInt();
		y = input.readUnsignedByte();
		z = input.readInt();
		face = input.readByte();
	}

}

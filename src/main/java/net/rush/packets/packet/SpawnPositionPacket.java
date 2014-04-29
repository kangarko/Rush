package net.rush.packets.packet;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.rush.model.Position;
import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class SpawnPositionPacket extends Packet {
	public SpawnPositionPacket() {
		// TODO Auto-generated constructor stub
	}

	@Serialize(type = Type.INT, order = 0)
	private int x;
	@Serialize(type = Type.INT, order = 1)
	private int y;
	@Serialize(type = Type.INT, order = 2)
	private int z;

	public SpawnPositionPacket(Position pos) {
		super();
		x = (int) pos.getX();
		y = (int) pos.getY();
		z = (int) pos.getZ();
	}

	public int getOpcode() {
		return 0x06;
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

	public String getToStringDescription() {
		return String.format("x=\"%d\",y=\"%d\",z=\"%d\"", x, y, z);
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

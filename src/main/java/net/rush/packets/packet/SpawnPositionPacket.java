package net.rush.packets.packet;

import net.rush.model.Position;
import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class SpawnPositionPacket extends Packet {
	@Serialize(type = Type.INT, order = 0)
	private final int x;
	@Serialize(type = Type.INT, order = 1)
	private final int y;
	@Serialize(type = Type.INT, order = 2)
	private final int z;

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
}

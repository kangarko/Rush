package net.rush.packets.packet;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class PlayerPositionPacket extends Packet {
	@Serialize(type = Type.DOUBLE, order = 0)
	private final double x;
	@Serialize(type = Type.DOUBLE, order = 1)
	private final double y;
	@Serialize(type = Type.DOUBLE, order = 2)
	private final double stance;
	@Serialize(type = Type.DOUBLE, order = 3)
	private final double z;
	@Serialize(type = Type.BOOL, order = 4)
	private final boolean onGround;

	public PlayerPositionPacket(double x, double y, double stance, double z, boolean onGround) {
		super();
		this.x = x;
		this.y = y;
		this.stance = stance;
		this.z = z;
		this.onGround = onGround;
	}

	public int getOpcode() {
		return 0x0B;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getStance() {
		return stance;
	}

	public double getZ() {
		return z;
	}

	public boolean getOnGround() {
		return onGround;
	}

	public String getToStringDescription() {
		return String.format("x=\"%f\",y=\"%f\",stance=\"%f\",z=\"%f\",onGround=\"%b\"", x, y, stance, z, onGround);
	}
}

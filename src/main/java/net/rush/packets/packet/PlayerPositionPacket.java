package net.rush.packets.packet;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

import java.io.IOException;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class PlayerPositionPacket extends Packet {
	
	public PlayerPositionPacket() {
	}

	@Serialize(type = Type.DOUBLE, order = 0)
	private double x;
	@Serialize(type = Type.DOUBLE, order = 1)
	private double y;
	@Serialize(type = Type.DOUBLE, order = 2)
	private double stance;
	@Serialize(type = Type.DOUBLE, order = 3)
	private double z;
	@Serialize(type = Type.BOOL, order = 4)
	private boolean onGround;

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

	@Override
	public void read17(ByteBufInputStream input) throws IOException {
		x = input.readDouble();
		y = input.readDouble();
		stance = input.readDouble();
		z = input.readDouble();
		onGround = input.readBoolean();
	}
	
	@Override
	public void write17(ByteBufOutputStream output) throws IOException {
		output.writeDouble(x);
		output.writeDouble(y);
		output.writeDouble(stance);
		output.writeDouble(z);
		output.writeBoolean(onGround);
	}
}

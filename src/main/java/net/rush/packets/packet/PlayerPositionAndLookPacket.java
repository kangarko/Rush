package net.rush.packets.packet;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

import java.io.IOException;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class PlayerPositionAndLookPacket extends Packet {

	public PlayerPositionAndLookPacket() {
	}

	@Serialize(type = Type.DOUBLE, order = 0)
	private double x;
	@Serialize(type = Type.DOUBLE, order = 1)
	private double yOrStance;
	@Serialize(type = Type.DOUBLE, order = 2)
	private double stanceOrY;
	@Serialize(type = Type.DOUBLE, order = 3)
	private double z;
	@Serialize(type = Type.FLOAT, order = 4)
	private float yaw;
	@Serialize(type = Type.FLOAT, order = 5)
	private float pitch;
	@Serialize(type = Type.BOOL, order = 6)
	private boolean onGround;

	public PlayerPositionAndLookPacket(double x, double yOrStance, double stanceOrY, double z, float yaw, float pitch, boolean onGround) {
		super();
		this.x = x;
		this.yOrStance = yOrStance;
		this.stanceOrY = stanceOrY;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
		this.onGround = onGround;
	}

	public int getOpcode() {
		return 0x0D; // packet 13 / 0x0D
	}

	public double getX() {
		return x;
	}

	public double getYOrStance() {
		return yOrStance;
	}

	public double getStanceOrY() {
		return stanceOrY;
	}

	public double getZ() {
		return z;
	}

	public float getYaw() {
		return yaw;
	}

	public float getPitch() {
		return pitch;
	}

	public boolean getOnGround() {
		return onGround;
	}

	public String getToStringDescription() {
		return String.format("x=\"%f\",yOrStance=\"%f\",stanceOrY=\"%f\",z=\"%f\",yaw=\"%f\",pitch=\"%f\",onGround=\"%b\"", x, yOrStance, stanceOrY, z, yaw, pitch, onGround);
	}

	@Override
	public void read17(ByteBufInputStream input) throws IOException {
		x = input.readDouble();
		yOrStance = input.readDouble();
		stanceOrY = input.readDouble();
		z = input.readDouble();
		yaw = input.readFloat();
		pitch = input.readFloat();
		onGround = input.readBoolean();
	}

	@Override
	public void write17(ByteBufOutputStream output) throws IOException {
		output.writeDouble(this.x);
		output.writeDouble(this.yOrStance); //feet height ??
		//output.writeDouble(1.62D); // head height ??
		output.writeDouble(this.z);
		output.writeFloat(this.yaw);
		output.writeFloat(this.pitch);
		output.writeBoolean(this.onGround);
	}
}

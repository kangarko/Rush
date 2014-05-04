package net.rush.packets.packet;

import io.netty.buffer.ByteBufInputStream;

import java.io.IOException;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class SteerVehiclePacket extends Packet {
	
	public SteerVehiclePacket() {
	}

	@Serialize(type = Type.FLOAT, order = 0)
	private float sideways;
	@Serialize(type = Type.FLOAT, order = 1)
	private float forward;
	@Serialize(type = Type.BOOL, order = 2)
	private boolean jump;
	@Serialize(type = Type.BOOL, order = 3)
	private boolean unmount;

	public SteerVehiclePacket(float sideways, float forward, boolean jump, boolean unmount) {
		super();
		this.sideways = sideways;
		this.forward = forward;
		this.jump = jump;
		this.unmount = unmount;
	}

	public int getOpcode() {
		return 0x39;
	}

	public float getSideways() {
		return sideways;
	}

	public float getForward() {
		return forward;
	}

	public boolean getJump() {
		return jump;
	}

	public boolean getUnmount() {
		return unmount;
	}

	public String getToStringDescription() {
		return String.format("sideways=%d,forward=%d,jump=%b,unmount=%b", sideways, forward, jump, unmount);
	}

	@Override
	public void read17(ByteBufInputStream input) throws IOException {
		sideways = input.readFloat();
		forward = input.readFloat();
		jump = input.readBoolean();
		unmount = input.readBoolean();
	}
}

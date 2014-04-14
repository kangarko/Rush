package net.rush.packets.packet;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class SteerVehiclePacket extends Packet {
	@Serialize(type = Type.FLOAT, order = 0)
	private final float sideways;
	@Serialize(type = Type.FLOAT, order = 1)
	private final float forward;
	@Serialize(type = Type.BOOL, order = 2)
	private final boolean jump;
	@Serialize(type = Type.BOOL, order = 3)
	private final boolean unmount;

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
}

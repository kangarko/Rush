package net.rush.packets.packet.impl;

import net.rush.packets.packet.SteerVehiclePacket;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class SteerVehiclePacketImpl extends AbstractPacket implements SteerVehiclePacket {
	@Serialize(type = Type.FLOAT, order = 0)
	private final float sideways;
	@Serialize(type = Type.FLOAT, order = 1)
	private final float forward;
	@Serialize(type = Type.BOOL, order = 2)
	private final boolean jump;
	@Serialize(type = Type.BOOL, order = 3)
	private final boolean unmount;

	public SteerVehiclePacketImpl(float sideways, float forward, boolean jump, boolean unmount) {
		super();
		this.sideways = sideways;
		this.forward = forward;
		this.jump = jump;
		this.unmount = unmount;
	}

	@Override
	public int getOpcode() {
		return 0x39;
	}

	@Override
	public float getSideways() {
		return sideways;
	}

	@Override
	public float getForward() {
		return forward;
	}

	@Override
	public boolean getJump() {
		return jump;
	}

	@Override
	public boolean getUnmount() {
		return unmount;
	}

	@Override
	public String getToStringDescription() {
		return String.format("sideways=%d,forward=%d,jump=%b,unmount=%b", 
				sideways, forward, jump, unmount);
	}
}

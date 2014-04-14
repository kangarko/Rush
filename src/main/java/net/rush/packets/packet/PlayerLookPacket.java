package net.rush.packets.packet;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class PlayerLookPacket extends Packet {
	@Serialize(type = Type.FLOAT, order = 0)
	private final float yaw;
	@Serialize(type = Type.FLOAT, order = 1)
	private final float pitch;
	@Serialize(type = Type.BOOL, order = 2)
	private final boolean onGround;

	public PlayerLookPacket(float yaw, float pitch, boolean onGround) {
		super();
		this.yaw = yaw;
		this.pitch = pitch;
		this.onGround = onGround;
	}

	public int getOpcode() {
		return 0x0C;
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
		return String.format("yaw=\"%f\",pitch=\"%f\",onGround=\"%b\"", yaw, pitch, onGround);
	}
}

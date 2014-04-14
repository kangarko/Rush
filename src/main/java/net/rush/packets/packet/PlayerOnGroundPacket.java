package net.rush.packets.packet;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class PlayerOnGroundPacket extends Packet {
	@Serialize(type = Type.BOOL, order = 0)
	private final boolean onGround;

	public PlayerOnGroundPacket(boolean onGround) {
		super();
		this.onGround = onGround;
	}

	public int getOpcode() {
		return 0x0A;
	}

	public boolean getOnGround() {
		return onGround;
	}

	public String getToStringDescription() {
		return String.format("onGround=\"%b\"", onGround);
	}
}
